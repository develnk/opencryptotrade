package com.opencryptotrade.common.events.annotation;

import com.opencryptotrade.common.model.EventName;

import java.lang.annotation.*;

/**
 *
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Event {

    /**
     * The name of the application event. The default is NONE, which implies that the handler
     * implementation is expected to provide the correct name of the event.
     */
    EventName name() default EventName.NONE;


    /**
     * The suggested default message the handler may use to create the event; handler implementations may choose to ignore. The default is an empty
     * message.
     */
    String message() default "";

}
