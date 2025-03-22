package xyz.herberto.chatFilter.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CC {


    private static final Pattern HEX_PATTERN = Pattern.compile("#([A-Fa-f0-9]{6})");

    public static Component translate(String input) {
        Matcher matcher = HEX_PATTERN.matcher(input);
        while (matcher.find()) {
            String hexCode = matcher.group(1);
            TextColor color = TextColor.fromHexString("#" + hexCode);
            input = input.replace("#" + hexCode, LegacyComponentSerializer.legacySection().serialize(Component.text("", color)));
        }

        return LegacyComponentSerializer.legacyAmpersand().deserialize(input);
    }

}
