// Patrick Neves - 301367126 COMP303 Assignment 4, December 4, 2025
package com.assignment4.manager.model;

import lombok.Data;

@Data
public class HttpErrorBody {
    private String message;

    public HttpErrorBody() {
    }
    
    public HttpErrorBody(String message) {
    	this.message = message;
    }
}
