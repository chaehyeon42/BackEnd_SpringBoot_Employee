package com.employee.api.service.common;

import com.employee.api.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;

public class CommonService {
    //public <X extends Throwable> T orElseT    hrow(Supplier<? extends X> exceptionSupplier)
    //Supplier 추상메서드 T get()
    public static ResourceNotFoundException getNotFoundException(String msg,
                                                                  Long entityId) {
        return new ResourceNotFoundException( msg + entityId,HttpStatus.NOT_FOUND);
    }
}
