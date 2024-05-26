package io.marregui.arrays;

public class SimplifyPath {

    // Given a string path, which is an absolute path (starting with a slash '/')
    // to a file or directory in a Unix-style file system, convert it to the
    // simplified canonical path.
    //
    //In a Unix-style file system, a period '.' refers to the current directory,
    // a double period '..' refers to the directory up a level, and any multiple
    // consecutive slashes (i.e. '//') are treated as a single slash '/'. For
    // this problem, any other format of periods such as '...' are treated as
    // file/directory names.
    //
    //The canonical path should have the following format:
    //
    // The path starts with a single slash '/'.
    // Any two directories are separated by a single slash '/'.
    // The path does not end with a trailing '/'.
    // The path only contains the directories on the path from the root directory
    // to the target file or directory (i.e., no period '.' or double period '..')
    // Return the simplified canonical path.


    private static int[] extend(int[] array) {
        int[] t = new int[array.length * 2];
        System.arraycopy(array, 0, t, 0, array.length);
        return t;
    }

    private static int addSubsequence(int[] array, int idx, int start, int limit) {
        array[idx++] = start;
        array[idx++] = limit;
        return idx;
    }

    public static String simplifyPath(String path) {
        int[] startEnd = new int[16]; // start/end dir name
        int seIdx = 0;
        int dirStart = 1;
        int limit = path.length();
        char p0 = '\0';
        char p1 = '\0';
        for (int i = 1; i < limit; i++) {
            char c = path.charAt(i);
            if (c == '/') {
                int len = i - dirStart;
                if (len == 1 && (p1 == '.' || p1 == '/')) {
                    // do nothing
                } else if (len == 2 && i > 1 && p0 == p1 && p0 == '.') {
                    if (seIdx > 0) {
                        seIdx -= 2;
                    }
                } else if (len > 0) {
                    if (seIdx >= startEnd.length) {
                        startEnd = extend(startEnd);
                    }
                    seIdx = addSubsequence(startEnd, seIdx, dirStart, i);
                }
                dirStart = i + 1;
            }
            p0 = p1;
            p1 = c;
        }
        if (dirStart < limit) {
            int len = limit - dirStart;
            if (len == 1 && (p1 == '.' || p1 == '/')) {
                // do nothing
            } else if (len == 2 && limit > 1 && p0 == p1 && p0 == '.') {
                if (seIdx > 0) {
                    seIdx -= 2;
                }
            } else if (len > 0) {
                if (seIdx >= startEnd.length) {
                    startEnd = extend(startEnd);
                }
                seIdx = addSubsequence(startEnd, seIdx, dirStart, limit);
            }
        }

        StringBuilder sb = new StringBuilder("/");
        for (int i = 0; i < seIdx; i += 2) {
            CharSequence part = path.subSequence(startEnd[i], startEnd[i + 1]);
            sb.append(part);
            if (i + 2 < seIdx) {
                sb.append('/');
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(simplifyPath("/home//foo/"));
    }
}
