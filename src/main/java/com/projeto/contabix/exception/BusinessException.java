package br.poo.unipac.uberlandia.ematriculasserver.exception;

public class BusinessException extends RuntimeException {

    private String code;

    private String message;

    public BusinessException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
