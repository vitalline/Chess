package com.syntech.chess.text;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public enum Translation {
    NONE(0),
    EN_US(1),
    RU_RU(2);

    private static final ArrayList<String> strings = new ArrayList<>(Arrays.asList(
            "code", "en_US", "ru_RU",
            "language#short", "ENG", "РУС",
            "language", "English", "Русский",

            "chess#short", "Regular Chess", "Обычные шахматы",
            "chess", "Chess", "Шахматы",
            "cyclic_chess#short", "Cyclic Chess", "Цилиндрические шахматы",
            "cyclic_chess", "Cyclic Chess", "Цилиндрические шахматы",

            "forced_chess#short", "Forced Chess", "Форсированные шахматы",
            "forced_chess", "Forced Chess", "Форсированные шахматы",
            "modest_forced_chess#short", "Modest Forced Chess", "Скромные ФШ",
            "modest_forced_chess", "Modest Forced Chess", "Скромные ФШ",
            "force_major_chess#short", "Force Major Chess", "Форс-мажорные шахматы",
            "force_major_chess", "Force Major Chess", "Форс-мажорные шахматы",
            "force_minor_chess#short", "Force Minor Chess", "Форс-минорные шахматы",
            "force_minor_chess", "Force Minor Chess", "Форс-минорные шахматы",
            "cyclic_modest_forced_chess#short", "Cyclic Modest Frc. Ch.", "Скромные цилиндр. ФШ",
            "cyclic_modest_forced_chess", "Cyclic Modest Forced Chess", "Скромные цилиндрические ФШ",
            "classical_chess_4x4#short", "Classical Chess 4×4", "Шахматы 4×4",
            "classical_chess_4x4", "Classical Chess 4×4", "Классические шахматы 4×4",

            "sniper_forced_chess#short", "Sniper Forced Chess", "Снайперские ФШ",
            "sniper_forced_chess", "Sniper Forced Chess", "Снайперские ФШ",
            "modest_sniper_forced_chess#short", "Modest Sniper Frc. Ch.", "Скромные снайперские ФШ",
            "modest_sniper_forced_chess", "Modest Sniper Forced Chess", "Скромные снайперские ФШ",
            "reserve_forced_chess#short", "Reserve Forced Chess", "Резервные ФШ",
            "reserve_forced_chess", "Reserve Forced Chess", "Резервные ФШ",
            "modest_reserve_forced_chess#short", "Modest Reserve Frc. Ch.", "Скромные резервные ФШ",
            "modest_reserve_forced_chess", "Modest Reserve Forced Chess", "Скромные резервные ФШ",
            "pegasi_reserve_forced_chess#short", "Pegasi Reserve Frc. Ch.", "Резервные ФШ с Пегасами",
            "pegasi_reserve_forced_chess", "Pegasi Reserve Forced Chess", "Резервные ФШ с Пегасами",
            "sniper_reserve_forced_chess#short", "Sniper-Reserve Frc. Ch.", "Снайперские резервные ФШ",
            "sniper_reserve_forced_chess", "Sniper-Reserve Forced Chess", "Снайперские резервные ФШ",
            "sniper_pegasi_reserve_forced_chess#short", "Snpr.-Pgs.-Rsrv. Frc. Ch.", "Снайп. рез. ФШ с Пегасами",
            "sniper_pegasi_reserve_forced_chess", "Sniper-Pegasi-Reserve Forced Chess", "Снайперские резервные ФШ с Пегасами",

            "involution_forced_chess#short", "Involution Forced Chess", "Инволюционные ФШ",
            "involution_forced_chess", "Involution Forced Chess", "Инволюционные ФШ",
            "mmorpg_forced_chess#short", "MMORPG Forced Chess", "ММОРПГ-шные ФШ",
            "mmorpg_forced_chess", "MMORPG Forced Chess", "ММОРПГ-шные ФШ",
            "involution_mmorpg_forced_chess#short", "Inv.-MMORPG Forced Chess", "Инвол. ММОРПГ-шные ФШ",
            "involution_mmorpg_forced_chess", "Involution-MMORPG Forced Chess", "Инволюционные ММОРПГ-шные ФШ",
            "resistance_mmorpg_forced_chess#short", "Resist. MMORPG Frc. Ch.", "Резист. ММОРПГ-шные ФШ",
            "resistance_mmorpg_forced_chess", "Resistance MMORPG Forced Chess", "Резистентные ММОРПГ-шные ФШ",
            "resistance_power_mmorpg_forced_chess#short", "Res.-Pow. MMORPG Frc. Ch.", "Рез.-прот. ММОРПГ-шные ФШ",
            "resistance_power_mmorpg_forced_chess", "Resistance-Power MMORPG Forced Chess", "Резистентно-противорезистентные ММОРПГ-шные ФШ",
            "resistance_involution_mmorpg_forced_chess#short", "Resist. Inv. MMORPG FC", "Рез. инв. ММОРПГ-шные ФШ",
            "resistance_involution_mmorpg_forced_chess", "Resistance Involution-MMORPG Forced Chess", "Резистентные инволюционные ММОРПГ-шные ФШ",
            "resistance_power_involution_mmorpg_forced_chess#short", "Res.-Pow. Inv. MMORPG FC", "Рез.-прот. инв. ММОРПГФШ",
            "resistance_power_involution_mmorpg_forced_chess", "Resistance-Power Involution-MMORPG Forced Chess", "Резистентно-противорезистентные инволюционные ММОРПГ-шные ФШ",
            "forced_involution_forced_chess#short", "\"Forced Inv.\" Frc. Ch.", "Форсиров.-инволюц. ФШ",
            "forced_involution_forced_chess", "\"Forced Involution\" Forced Chess", "Форсированно-инволюционные ФШ",
            "forced_involution_mmorpg_forced_chess#short", "\"Frc. Inv.\" MMORPG FC", "Форс.-инв.-ММОРПГ-шные ФШ",
            "forced_involution_mmorpg_forced_chess", "\"Forced Involution\" MMORPG Forced Chess", "Форсированно-инволюционные ММОРПГ-шные ФШ",

            "cloning_forced_chess#short", "Cloning Forced Chess", "Размножающиеся ФШ",
            "cloning_forced_chess", "Cloning Forced Chess", "Размножающиеся ФШ",

            "cyclic_chess_8x12#short", "Cyclic Chess 8×12", "Цилиндр. шахматы 8×12",
            "cyclic_chess_8x12", "Cyclic Chess 8×12", "Цилиндрические шахматы 8×12",
            "cyclic_chess_8x16#short", "Cyclic Chess 8×16", "Цилиндр. шахматы 8×16",
            "cyclic_chess_8x16", "Cyclic Chess 8×16", "Цилиндрические шахматы 8×16",

            "info", "Info", "Информация",

            "chess#info",
            "Just your regular plain old chess. Have fun!",
            "Просто обычные шахматы. Приятной игры!",

            //TODO: fill in the "#info" translation fields

            "cyclic_chess#info",
            "",
            "",

            "forced_chess#info",
            "",
            "",

            "modest_forced_chess#info",
            "",
            "",

            "force_major_chess#info",
            "",
            "",

            "force_minor_chess#info",
            "",
            "",

            "cyclic_modest_forced_chess#info",
            "",
            "",

            "classical_chess_4x4#info",
            "",
            "",

            "sniper_forced_chess#info",
            "",
            "",

            "modest_sniper_forced_chess#info",
            "",
            "",

            "reserve_forced_chess#info",
            "",
            "",

            "modest_reserve_forced_chess#info",
            "",
            "",

            "pegasi_reserve_forced_chess#info",
            "",
            "",

            "sniper_reserve_forced_chess#info",
            "",
            "",

            "sniper_pegasi_reserve_forced_chess#info",
            "",
            "",

            "involution_forced_chess#info",
            "",
            "",

            "mmorpg_forced_chess#info",
            "",
            "",

            "involution_mmorpg_forced_chess#info",
            "",
            "",

            "resistance_mmorpg_forced_chess#info",
            "",
            "",

            "resistance_power_mmorpg_forced_chess#info",
            "",
            "",

            "resistance_involution_mmorpg_forced_chess#info",
            "",
            "",

            "resistance_power_involution_mmorpg_forced_chess#info",
            "",
            "",

            "forced_involution_forced_chess#info",
            "",
            "",

            "forced_involution_mmorpg_forced_chess#info",
            "",
            "",

            "cloning_forced_chess#info",
            "",
            "",

            "cyclic_chess_8x12#info",
            "",
            "",

            "cyclic_chess_8x16#info",
            "",
            "",

            "side_white", "White", "белых",
            "side_black", "Black", "чёрных",

            "type_none", "", "",
            "type_forced", "Forced", "Форс.",
            "type_modest_forced", "Modest Forced", "Скромн. Форс.",
            "type_cloning_forced", "Cloning Forced", "Размн. Форс.",

            "piece_none", "none", "нет",
            "piece_empty", "Empty Cell", "Пустая клетка",
            "piece_wall", "Wall", "Стена",
            "piece_pawn", "Pawn", "Пешка",
            "piece_knight", "Knight", "Конь",
            "piece_pegasus", "Pegasus", "Пегас",
            "piece_bishop", "Bishop", "Слон",
            "piece_sniper", "Sniper", "Снайпер",
            "piece_rook", "Rook", "Ладья",
            "piece_queen", "Queen", "Ферзь",
            "piece_king", "King", "Король",
            "piece_amazon", "Amazon", "Магараджа",

            "log_pawn", "", "",
            "log_knight", "N", "К",
            "log_pegasus", "P", "П",
            "log_bishop", "B", "С",
            "log_sniper", "S", "С",
            "log_rook", "R", "Л",
            "log_queen", "Q", "Ф",
            "log_king", "K", "Кр",
            "log_amazon", "A", "М",

            "label_name", "%s %s %s", "%s %3$s %2$s",
            "label_xp", "[ XP: %d/%d ]", "[ ОП: %d/%d ]",
            "label_next_level", "Next level: %s", "След. уровень: %s",
            "label_resistance_xp", "[ Res.XP: %d/%d ]", "[ Рез.ОП: %d/%d ]",
            "label_resistant_to", "Resistant to: %s", "Защита от: %s",
            "label_power_xp", "[ Pow.XP: %d/%d ]", "[ Сил.ОП: %d/%d ]",
            "label_power_level", "Power level: %s", "Уровень силы: %s",
            "label_started_on", "Started on %s", "Нач. позиция: %s",
            "label_will_respawn_as", "Will respawn as %s", "Реинкарнация: %s",
            "label_worth_xp", "Worth %d XP", "ОП за взятие: %d",
            "label_invincible", "Invincible", "Неуязвимая фигура",

            "status_game", "Game: %s", "Режим: %s",
            "status_turn", "%s's Turn", "Ход %s",
            "status_check", "%s is in check!", "Объявлен шах игроку за %s!",
            "status_checkmate", "Checkmate! %s wins!", "Шах и мат! Победа %s!",
            "status_stalemate", "%s has stalemated %s!", "У %2$s пат!",

            "action_ok", "OK", "ОК",
            "action_undo", "Undo last move", "Отменить ход",
            "action_redo", "Redo next move", "Повторить ход",
            "action_next", "Next page", "Следующая страница",
            "action_previous", "Previous page", "Предыдущая страница",
            "action_open_log", "Show moves", "Показать ходы",
            "action_close_log", "Hide moves", "Скрыть ходы",
            "action_info", "Info", "Информация",
            "action_random", "Random move", "Случайный ход",
            "action_restart", "New game", "Новая игра",
            "action_return", "Return to menu", "Вернуться в меню"
    ));
    private final int offset;

    Translation(int offset) {
        this.offset = offset;
    }

    @NotNull
    public final String get(String str) {
        int index = strings.indexOf(str);
        if (index == -1) {
            return "[" + str + "]";
        } else {
            return strings.get(strings.indexOf(str) + offset);
        }
    }

    // when in doubt, delet this
    @NotNull
    public final String getPadded(String str) {
        int index = strings.indexOf(str);
        if (index == -1) {
            return "{" + str + "}";
        } else {
            String rawTranslation = strings.get(strings.indexOf(str) + offset);
            String padding = new String(new char[get("status_game").replace("%s", "").length()]).replace("\0", " ");
            return rawTranslation.replace("\n", "\n" + padding);
        }
    }
}
