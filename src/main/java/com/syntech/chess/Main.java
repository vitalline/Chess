package com.syntech.chess;

import com.syntech.chess.graphic.CellGraphics;
import com.syntech.chess.graphic.Color;
import com.syntech.chess.ui.BaseUI;
import imgui.*;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

@SuppressWarnings("MagicNumber")
public final class Main {
    private long windowPtr; // pointer to the current GLFW window

    // LWJGJ3 window backend
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw();

    // LWJGL3 renderer (SHOULD be initialized)
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();
    private String glslVersion = null; // We can initialize our renderer with different versions of the GLSL

    private final int width = 1366, height = 768;

    // User UI to render
    private final BaseUI baseUI = new BaseUI(width, height);

    public void run() throws Exception {
        setupGlfw();
        setupImGui();

        // Method initializes GLFW backend.
        // This method SHOULD be called after you've setup GLFW.
        // ImGui context should be created as well.
        imGuiGlfw.init(windowPtr, true);
        // Method initializes LWJGL3 renderer.
        // This method SHOULD be called after you've initialized your ImGui configuration (fonts and so on).
        // ImGui context should be created as well.
        imGuiGl3.init(glslVersion);

        CellGraphics.initialize();

        loop();

        baseUI.stopExtraThreads();

        // You should clean up after yourself in reverse order.
        imGuiGl3.dispose();
        imGuiGlfw.dispose();

        ImGui.destroyContext();

        disposeWindow();
    }

    // Initialize GLFW + create an OpenGL context.
    // All code is mostly a copy-paste from the official LWJGL3 "Get Started": https://www.lwjgl.org/guide
    private void setupGlfw() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation

        decideGlGlslVersions();

        windowPtr = glfwCreateWindow(width, height, "Chess", NULL, NULL);

        if (windowPtr == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Get the thread stack and push a new frame
        try (MemoryStack stack = stackPush()) {
            final IntBuffer pWidth = stack.mallocInt(1); // int*
            final IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(windowPtr, pWidth, pHeight);

            // Get the resolution of the primary monitor
            final GLFWVidMode vidmode = Objects.requireNonNull(glfwGetVideoMode(glfwGetPrimaryMonitor()));

            // Center the window
            glfwSetWindowPos(windowPtr, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
        } // the stack frame is popped automatically

        glfwMakeContextCurrent(windowPtr); // Make the OpenGL context current
        glfwSwapInterval(GLFW_TRUE); // Enable v-sync
        glfwShowWindow(windowPtr); // Make the window visible

        // IMPORTANT!!
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
    }

    private void decideGlGlslVersions() {
        final boolean isMac = System.getProperty("os.name").toLowerCase().contains("mac");
        if (isMac) {
            glslVersion = "#version 150";
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);  // 3.2+ only
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);            // Required on Mac
        } else {
            glslVersion = "#version 130";
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 0);
        }
    }

    // Initialize Dear ImGui.
    private void setupImGui() throws IOException {
        // IMPORTANT!!
        // This line is critical for Dear ImGui to work.
        ImGui.createContext();

        // ------------------------------------------------------------
        // Initialize ImGuiIO config
        final ImGuiIO io = ImGui.getIO();

        io.setIniFilename(null); // We don't want to save .ini file
        //io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);  // Enable Keyboard Controls
        //io.addConfigFlags(ImGuiConfigFlags.DockingEnable);      // Enable Docking
        //io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable);    // Enable Multi-Viewport / Platform Windows
        io.setConfigViewportsNoTaskBarIcon(true);

        // ------------------------------------------------------------
        // Fonts configuration
        // Read: https://raw.githubusercontent.com/ocornut/imgui/master/docs/FONTS.txt

        final ImFontAtlas fontAtlas = io.getFonts();
        final ImFontConfig fontConfig = new ImFontConfig(); // Natively allocated object, should be explicitly destroyed

        // Glyphs could be added per-font as well as per config used globally like here
        short[] glyphRangesExtra = new short[]{'…', '…'};
        short[] glyphRangesCyrillic = fontAtlas.getGlyphRangesCyrillic();
        short[] glyphRanges = Arrays.copyOf(glyphRangesExtra, glyphRangesExtra.length + glyphRangesCyrillic.length);
        System.arraycopy(glyphRangesCyrillic, 0, glyphRanges, glyphRangesExtra.length, glyphRangesCyrillic.length);
        fontConfig.setGlyphRanges(glyphRanges);

        /*

        // Add a default font, which is 'ProggyClean.ttf, 13px'
        fontAtlas.addFontDefault();

        // Fonts merge example
        fontConfig.setMergeMode(true); // When enabled, all fonts added with this config would be merged with the previously added font
        fontConfig.setPixelSnapH(true);

        fontAtlas.addFontFromMemoryTTF(loadFromResources("basis33.ttf"), 16, fontConfig);

        fontConfig.setMergeMode(false);
        fontConfig.setPixelSnapH(false);

        // Fonts from file/memory example
        // We can add new fonts from the file system
        fontAtlas.addFontFromFileTTF("src/test/resources/Righteous-Regular.ttf", 14, fontConfig);
        fontAtlas.addFontFromFileTTF("src/test/resources/Righteous-Regular.ttf", 16, fontConfig);

        // Or directly from the memory
        fontConfig.setName("Roboto-Regular.ttf, 14px"); // This name will be displayed in Style Editor
        fontAtlas.addFontFromMemoryTTF(loadFromResources("Roboto-Regular.ttf"), 14, fontConfig);
        fontConfig.setName("Roboto-Regular.ttf, 16px"); // We can apply a new config value every time we add a new font
        fontAtlas.addFontFromMemoryTTF(loadFromResources("Roboto-Regular.ttf"), 16, fontConfig);

         */

        fontConfig.setGlyphOffset(2, -2);
        fontAtlas.addFontFromMemoryTTF(loadFromResources("fonts/PureProg 12.ttf"), 25, fontConfig);

        fontConfig.destroy(); // After all fonts were added we don't need this config more

        // When viewports are enabled we tweak WindowRounding/WindowBg so platform windows can look identical to regular ones.
        if (io.hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final ImGuiStyle style = ImGui.getStyle();
            style.setWindowRounding(0.0f);
            style.setColor(ImGuiCol.WindowBg, ImGui.getColorU32(ImGuiCol.WindowBg, 1));
        }
    }

    // Main application loop
    private void loop() throws Exception {
        // Run the rendering loop until the user has attempted to close the window
        while (!glfwWindowShouldClose(windowPtr)) {
            startFrame();

            // Any Dear ImGui code SHOULD go between ImGui.newFrame()/ImGui.render() methods
            imGuiGlfw.newFrame();
            ImGui.newFrame();

            baseUI.render();
            ImGui.render();

            endFrame();
        }
    }

    private void startFrame() {
        // Set the clear color and clear the window
        ImVec4 backgroundColor = Color.backgroundColor;
        glClearColor(backgroundColor.x, backgroundColor.y, backgroundColor.z, 0.0f);
        glClear(GL_COLOR_BUFFER_BIT);
    }

    private void endFrame() {
        // After Dear ImGui prepared a draw data, we use it in the LWJGL3 renderer.
        // At that moment ImGui will be rendered to the current OpenGL context.
        imGuiGl3.renderDrawData(ImGui.getDrawData());

        if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
            final long backupWindowPtr = glfwGetCurrentContext();
            ImGui.updatePlatformWindows();
            ImGui.renderPlatformWindowsDefault();
            glfwMakeContextCurrent(backupWindowPtr);
        }

        glfwSwapBuffers(windowPtr);
        glfwPollEvents();
    }

    private void disposeWindow() {
        glfwFreeCallbacks(windowPtr);
        glfwDestroyWindow(windowPtr);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }

    private byte @NotNull [] loadFromResources(final String fileName) {
        try (InputStream is = Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream(fileName));
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            final byte[] data = new byte[16384];

            int nRead;
            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            return buffer.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public static void main(final String[] args) throws Exception {
        new Main().run();
    }
}