else if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') || ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '>' || ch == '<' || ch == '=' || ch == '?') {
    int i = 0;
    while (true) {
        buf[i++] = (byte)ch;
        ch = in.read();
        if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z') ||
            (ch >= '0' && ch <= '9') ||
            ch == '+' || ch == '-' || ch == '*' || ch == '/' ||
            ch == '>' || ch == '<' || ch == '=' || ch == '?') {
            continue;
        } else {
            in.unread(ch);
            break;
        }
    }
    return new IdentToken(new String(buf, 0, i).toLowerCase());
}
