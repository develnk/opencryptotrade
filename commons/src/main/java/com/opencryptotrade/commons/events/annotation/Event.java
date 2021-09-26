package com.opencryptotrade.commons.events.annotation;

import com.opencryptotrade.commons.model.EventName;

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
     * <p><br>
     * Custom messages may use place-holder variables to be resolved by the implementations of {@link com.elromco.moveboard.events.service.handler.EventHandler}.
     * The following placeholders are available to be replaced at runtime with the corresponding values if the latter are detected in the event object
     * generated by the {@link com.elromco.moveboard.events.service.handler.EventHandler} implementation right before it returns the new event:
     * <ul>
     *     <li>${accountId}</li> - will be replaced with the {@code ApplicationEvent.accountID} value if available
     *     <li>${orderNumber}</li> - will be replaced with the {@code ApplicationEvent.orderNumber} value if available
     *     <li>${currentUser}</li>  -  will be replaced with the {@code MoveboardRequestContext.getCurrentUserFullName()} value  if available
     *     <li>${referencedUser}</li> - will be replaced with the {@code ApplicationEvent.referencedUser.fullName} value if available
     * </ul>
     */
    String message() default "";

}