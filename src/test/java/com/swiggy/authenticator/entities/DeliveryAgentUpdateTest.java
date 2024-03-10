package com.swiggy.authenticator.entities;

import com.swiggy.authenticator.enums.DeliveryAgentStatus;
import com.swiggy.authenticator.enums.DeliveryAgentUpdateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.swiggy.authenticator.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class DeliveryAgentUpdateTest {
    private DeliveryAgent mockedDeliveryAgent = spy(new DeliveryAgent(TEST_DELIVERY_AGENT_USERNAME, TEST_DELIVERY_AGENT_PASSWORD, TEST_DELIVERY_AGENT_PINCODE));

    @BeforeEach
    public void setUp(){
        reset(this.mockedDeliveryAgent);
    }
    @Test
    public void test_shouldUpdateDeliveryAgentLocation(){
        assertDoesNotThrow(()->{
            new DeliveryAgentUpdate(this.mockedDeliveryAgent, DeliveryAgentUpdateType.LOCATION, TEST_CUSTOMER_PINCODE+1);
        });
        assertEquals(TEST_DELIVERY_AGENT_PINCODE+1, this.mockedDeliveryAgent.getCurrentLocationPincode());
        assertEquals(DeliveryAgentStatus.AVAILABLE, this.mockedDeliveryAgent.getStatus());
        verify(this.mockedDeliveryAgent, times(1)).setCurrentLocationPincode(TEST_DELIVERY_AGENT_PINCODE+1);
        verify(this.mockedDeliveryAgent, never()).setStatus(any(DeliveryAgentStatus.class));
    }

    @Test
    public void test_shouldUpdateDeliveryAgentStatus(){
        assertDoesNotThrow(()->{
            new DeliveryAgentUpdate(this.mockedDeliveryAgent, DeliveryAgentUpdateType.LOCATION, DeliveryAgentStatus.ENGAGED);
        });
        assertEquals(TEST_DELIVERY_AGENT_PINCODE, this.mockedDeliveryAgent.getCurrentLocationPincode());
        assertEquals(DeliveryAgentStatus.ENGAGED, this.mockedDeliveryAgent.getStatus());
        verify(this.mockedDeliveryAgent, never()).setCurrentLocationPincode(TEST_DELIVERY_AGENT_PINCODE+1);
        verify(this.mockedDeliveryAgent, times(1)).setStatus(any(DeliveryAgentStatus.class));
    }
}
