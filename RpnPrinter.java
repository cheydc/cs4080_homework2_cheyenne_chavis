package com.craftinginterpreters.lox;

class RpnPrinter implements Expr.Visitor<String> {

  String print(Expr expr) {
    return expr.accept(this);
  }

  @Override
  public String visitBinaryExpr(Expr.Binary expr) {
    return join(expr.left.accept(this),
                expr.right.accept(this),
                expr.operator.lexeme);
  }

  @Override
  public String visitGroupingExpr(Expr.Grouping expr) {
    return expr.expression.accept(this);
  }

  @Override
  public String visitLiteralExpr(Expr.Literal expr) {
    if (expr.value == null) return "nil";
    return expr.value.toString();
  }

  @Override
  public String visitUnaryExpr(Expr.Unary expr) {
    // operand first, then operator (RPN)
    return join(expr.right.accept(this), expr.operator.lexeme);
  }

  @Override
  public String visitVariableExpr(Expr.Variable expr) {
    return expr.name.lexeme;
  }

  @Override
  public String visitAssignExpr(Expr.Assign expr) {
    // not really arithmetic RPN, but a reasonable postfix form:
    return join(expr.value.accept(this), expr.name.lexeme, "=");
  }

  private String join(String... parts) {
    return String.join(" ", parts);
  }
}