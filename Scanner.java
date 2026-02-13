case '/':
  if (match('/')) {
    // Line comment.
    while (peek() != '\n' && !isAtEnd()) advance();
  } else if (match('*')) {
    // Block comment (supports nesting).
    blockComment();
  } else {
    addToken(SLASH);
  }
  break;

private void blockComment() {
  int depth = 1;

  while (depth > 0) {
    if (isAtEnd()) {
      Lox.error(line, "Unterminated block comment.");
      return;
    }

    char c = advance();

    if (c == '\n') {
      line++;
      continue;
    }

    // Nested opener: /*
    if (c == '/' && peek() == '*') {
      advance();   // consume '*'
      depth++;
      continue;
    }

    // Closer: */
    if (c == '*' && peek() == '/') {
      advance();   // consume '/'
      depth--;
    }
  }
}