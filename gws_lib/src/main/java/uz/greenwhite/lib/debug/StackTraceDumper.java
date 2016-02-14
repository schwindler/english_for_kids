package uz.greenwhite.lib.debug;

import java.util.Map;
import java.util.Set;

public class StackTraceDumper {

    public static String dumpAllStackTraces() {
        StringBuilder sb = new StringBuilder();
        Set<Map.Entry<Thread, StackTraceElement[]>> entries = Thread.getAllStackTraces().entrySet();
        for (Map.Entry<Thread, StackTraceElement[]> entry : entries) {
            sb.append(entry.getKey().getName() + ":\n");
            for (StackTraceElement element : entry.getValue())
                sb.append("\t" + element + "\n");
        }
        return sb.toString();
    }
}