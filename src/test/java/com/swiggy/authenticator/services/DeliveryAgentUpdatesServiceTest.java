package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.DeliveryAgentUpdateDto;
import com.swiggy.authenticator.entities.DeliveryAgent;
import com.swiggy.authenticator.entities.DeliveryAgentUpdate;
import com.swiggy.authenticator.enums.DeliveryAgentUpdateType;
import com.swiggy.authenticator.repositories.DeliveryAgentUpdatesDao;
import com.swiggy.authenticator.repositories.DeliveryAgentsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;

import static com.swiggy.authenticator.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeliveryAgentUpdatesServiceTest {
    @Mock
    private DeliveryAgentUpdatesDao mockedDeliveryAgentUpdatesDao;

    @Mock
    private DeliveryAgentsService mockedDeliveryAgentsService;

    @InjectMocks
    private DeliveryAgentUpdatesService deliveryAgentUpdatesService;

    private final DeliveryAgent testDeliveryAgent = new DeliveryAgent(TEST_DELIVERY_AGENT_USERNAME, TEST_DELIVERY_AGENT_PASSWORD, TEST_DELIVERY_AGENT_PINCODE);

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }
    @Test
    public void test_shouldCreateAnUpdateOfLocationType(){
        DeliveryAgentUpdate update = new DeliveryAgentUpdate(this.testDeliveryAgent, DeliveryAgentUpdateType.LOCATION, TEST_DELIVERY_AGENT_PINCODE+1);
        when(this.mockedDeliveryAgentUpdatesDao.save(any(DeliveryAgentUpdate.class))).thenReturn(update);
        when(this.mockedDeliveryAgentsService.fetch(TEST_DELIVERY_AGENT_ID)).thenReturn(this.testDeliveryAgent);
        DeliveryAgentUpdateDto dto = new DeliveryAgentUpdateDto(Optional.of(TEST_DELIVERY_AGENT_PINCODE), Optional.empty());

        assertDoesNotThrow(()->{
            DeliveryAgent updatedAgent = this.deliveryAgentUpdatesService.create(TEST_DELIVERY_AGENT_ID, DeliveryAgentUpdateType.LOCATION, dto);
            assertEquals(this.testDeliveryAgent, updatedAgent);
        });
        verify(this.mockedDeliveryAgentUpdatesDao, times(1)).save(any(DeliveryAgentUpdate.class));
        verify(this.mockedDeliveryAgentsService, times(1)).fetch(TEST_DELIVERY_AGENT_ID);
    }
}
