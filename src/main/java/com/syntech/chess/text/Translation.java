package com.syntech.chess.text;

import java.util.ArrayList;
import java.util.Arrays;

public enum Translation {
    NONE(
            "code",
            "language#short",
            "language",

            "chess#short",
            "chess",
            "cyclic_chess#short",
            "cyclic_chess",
            "forced_chess#short",
            "forced_chess",
            "modest_forced_chess#short",
            "modest_forced_chess",
            "force_major_chess#short",
            "force_major_chess",
            "force_minor_chess#short",
            "force_minor_chess",
            "cyclic_modest_forced_chess#short",
            "cyclic_modest_forced_chess",
            "classical_chess_4x4#short",
            "classical_chess_4x4",

            "sniper_forced_chess#short",
            "sniper_forced_chess",
            "modest_sniper_forced_chess#short",
            "modest_sniper_forced_chess",
            "reserve_forced_chess#short",
            "reserve_forced_chess",
            "modest_reserve_forced_chess#short",
            "modest_reserve_forced_chess",
            "pegasi_reserve_forced_chess#short",
            "pegasi_reserve_forced_chess",
            "sniper_reserve_forced_chess#short",
            "sniper_reserve_forced_chess",
            "sniper_pegasi_reserve_forced_chess#short",
            "sniper_pegasi_reserve_forced_chess",

            "involution_forced_chess#short",
            "involution_forced_chess",
            "mmorpg_forced_chess#short",
            "mmorpg_forced_chess",
            "involution_mmorpg_forced_chess#short",
            "involution_mmorpg_forced_chess",
            "resistance_mmorpg_forced_chess#short",
            "resistance_mmorpg_forced_chess",
            "resistance_power_mmorpg_forced_chess#short",
            "resistance_power_mmorpg_forced_chess",
            "resistance_involution_mmorpg_forced_chess#short",
            "resistance_involution_mmorpg_forced_chess",
            "resistance_power_involution_mmorpg_forced_chess#short",
            "resistance_power_involution_mmorpg_forced_chess",
            "forced_involution_forced_chess#short",
            "forced_involution_forced_chess",
            "forced_involution_mmorpg_forced_chess#short",
            "forced_involution_mmorpg_forced_chess",

            "cloning_forced_chess#short",
            "cloning_forced_chess",

            "cyclic_chess_8x12#short",
            "cyclic_chess_8x12",
            "cyclic_chess_8x16#short",
            "cyclic_chess_8x16",

            "side_white",
            "side_black",

            "type_forced",
            "type_modest_forced",
            "type_cloning_forced",

            "piece_none",
            "piece_empty",
            "piece_wall",
            "piece_pawn",
            "piece_knight",
            "piece_pegasus",
            "piece_bishop",
            "piece_sniper",
            "piece_rook",
            "piece_queen",
            "piece_king",
            "piece_amazon",

            "label_name",
            "label_xp",
            "label_next_level",
            "label_resistance_xp",
            "label_resistant_to",
            "label_power_xp",
            "label_power_level",
            "label_started_on",
            "label_will_respawn_as",
            "label_worth_xp",
            "label_invincible",

            "status_game",
            "status_turn",
            "status_check",
            "status_checkmate",
            "status_stalemate",

            "action_undo",
            "action_close",
            "action_next",
            "action_previous"
    ),

    EN_US(
            "en_US",
            "ENG",
            "English",

            "Regular Chess",
            "Chess",
            "Cyclic Chess",
            "Cyclic Chess",
            "Forced Chess",
            "Forced Chess",
            "Modest Forced Chess",
            "Modest Forced Chess",
            "Force Major Chess",
            "Force Major Chess",
            "Force Minor Chess",
            "Force Minor Chess",
            "Cyclic Modest Frc. Ch.",
            "Cyclic Modest Forced Chess",
            "Classical Chess 4×4",
            "Classical Chess 4×4",

            "Sniper Forced Chess",
            "Sniper Forced Chess",
            "Modest Sniper Frc. Ch.",
            "Modest Sniper Forced Chess",
            "Reserve Forced Chess",
            "Reserve Forced Chess",
            "Modest Reserve Frc. Ch.",
            "Modest Reserve Forced Chess",
            "Pegasi Reserve Frc. Ch.",
            "Pegasi Reserve Forced Chess",
            "Sniper-Reserve Frc. Ch.",
            "Sniper-Reserve Forced Chess",
            "Snpr.-Pgs.-Rsrv. Frc. Ch.",
            "Sniper-Pegasi-Reserve Forced Chess",

            "Involution Forced Chess",
            "Involution Forced Chess",
            "MMORPG Forced Chess",
            "MMORPG Forced Chess",
            "Inv.-MMORPG Forced Chess",
            "Involution-MMORPG Forced Chess",
            "Resist. MMORPG Frc. Ch.",
            "Resistance MMORPG Forced Chess",
            "Res.-Pow. MMORPG Frc. Ch.",
            "Resistance-Power MMORPG Forced Chess",
            "Resist. Inv. MMORPG FC",
            "Resistance Involution-MMORPG Forced Chess",
            "Res.-Pow. Inv. MMORPG FC",
            "Resistance-Power Involution-MMORPG Forced Chess",
            "\"Forced Inv.\" Frc. Ch.",
            "\"Forced Involution\" Forced Chess",
            "\"Frc. Inv.\" MMORPG FC",
            "\"Forced Involution\" MMORPG Forced Chess",

            "Cloning Forced Chess",
            "Cloning Forced Chess",

            "Cyclic Chess 8×12",
            "Cyclic Chess 8×12",
            "Cyclic Chess 8×16",
            "Cyclic Chess 8×16",

            "White",
            "Black",

            "Forced",
            "Modest Forced",
            "Cloning Forced",

            "none",
            "Empty Cell",
            "Wall",
            "Pawn",
            "Knight",
            "Pegasus",
            "Bishop",
            "Sniper",
            "Rook",
            "Queen",
            "King",
            "Amazon",

            "%s %s %s",
            "[ XP: %d/%d ]",
            "Next level: %s",
            "[ Res.XP: %d/%d ]",
            "Resistant to: %s",
            "[ Pow.XP: %d/%d ]",
            "Power level: %s",
            "Started on %s",
            "Will respawn as %s",
            "Worth %d XP",
            "Invincible",

            "Game: %s",
            "%s's Turn",
            "%s is in check!",
            "Checkmate! %s wins!",
            "%s has stalemated %s!",

            "Undo last move",
            "Remove board",
            "Next page",
            "Previous page"
    ),

    RU_RU(
            "ru_RU",
            "РУС",
            "Русский",

            "Обычные шахматы",
            "Шахматы",
            "Цилиндрические шахматы",
            "Цилиндрические шахматы",
            "Форсированные шахматы",
            "Форсированные шахматы",
            "Скромные ФШ",
            "Скромные ФШ",
            "Форс-мажорные шахматы",
            "Форс-мажорные шахматы",
            "Форс-минорные шахматы",
            "Форс-минорные шахматы",
            "Скромные цилиндр. ФШ",
            "Скромные цилиндрические ФШ",
            "Шахматы 4×4",
            "Классические шахматы 4×4",

            "Снайперские ФШ",
            "Снайперские ФШ",
            "Скромные снайперские ФШ",
            "Скромные снайперские ФШ",
            "Резервные ФШ",
            "Резервные ФШ",
            "Скромные резервные ФШ",
            "Скромные резервные ФШ",
            "Резервные ФШ с Пегасами",
            "Резервные ФШ с Пегасами",
            "Снайперские резервные ФШ",
            "Снайперские резервные ФШ",
            "Снайп. рез. ФШ с Пегасами",
            "Снайперские резервные ФШ с Пегасами",

            "Инволюционные ФШ",
            "Инволюционные ФШ",
            "ММОРПГ-шные ФШ",
            "ММОРПГ-шные ФШ",
            "Инвол. ММОРПГ-шные ФШ",
            "Инволюционные ММОРПГ-шные ФШ",
            "Резист. ММОРПГ-шные ФШ",
            "Резистентные ММОРПГ-шные ФШ",
            "Рез.-прот. ММОРПГ-шные ФШ",
            "Резистентно-противорезистентные ММОРПГ-шные ФШ",
            "Рез. инв. ММОРПГ-шные ФШ",
            "Резистентные инволюционные ММОРПГ-шные ФШ",
            "Рез.-прот. инв. ММОРПГФШ",
            "Резистентно-противорезистентные инволюционные ММОРПГ-шные ФШ",
            "Форсиров.-инволюц. ФШ",
            "Форсированно-инволюционные ФШ",
            "Форс.-инв.-ММОРПГ-шные ФШ",
            "Форсированно-инволюционные ММОРПГ-шные ФШ",

            "Размножающиеся ФШ",
            "Размножающиеся ФШ",

            "Цилиндр. шахматы 8×12",
            "Цилиндрические шахматы 8×12",
            "Цилиндр. шахматы 8×16",
            "Цилиндрические шахматы 8×16",

            "белых",
            "чёрных",

            "Форс.",
            "Скромн. Форс.",
            "Размн. Форс.",

            "нет",
            "Пустая клетка",
            "Стена",
            "Пешка",
            "Конь",
            "Пегас",
            "Слон",
            "Снайпер",
            "Ладья",
            "Ферзь",
            "Король",
            "Магараджа",

            "%s %3$s %2$s",
            "[ ОП: %d/%d ]",
            "След. уровень: %s",
            "[ Рез.ОП: %d/%d ]",
            "Защита от: %s",
            "[ Сил.ОП: %d/%d ]",
            "Уровень силы: %s",
            "Нач. позиция: %s",
            "Реинкарнация: %s",
            "ОП за взятие: %d",
            "Неуязвимая фигура",

            "Режим: %s",
            "Ход %s",
            "Объявлен шах игроку за %s!",
            "Шах и мат! Победа %s!",
            "У %2$s пат!",

            "Вернуть ход",
            "Убрать доску",
            "Следующая страница",
            "Предыдущая страница"
    );

    private final ArrayList<String> translationStrings;

    Translation(String... strings) {
        translationStrings = new ArrayList<>(Arrays.asList(strings));
    }

    public String getRaw(String str) {
        try {
            return translationStrings.get(NONE.translationStrings.indexOf(str));
        } catch (IndexOutOfBoundsException ignored) {
            return str;
        }
    }

    public String get(String str) {
        try {
            String rawTranslation = translationStrings.get(NONE.translationStrings.indexOf(str));
            String padding = new String(new char[getRaw("status_game").replace("%s", "").length()]).replace("\0", " ");
            return rawTranslation.replace("\n", "\n" + padding);
        } catch (IndexOutOfBoundsException ignored) {
            return str;
        }
    }
}
