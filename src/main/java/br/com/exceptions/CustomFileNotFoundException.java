package br.com.exceptions;

public class CustomFileNotFoundException extends RuntimeException {
  public CustomFileNotFoundException() {
    super("Arquivo não encontrado.");
  }
}
