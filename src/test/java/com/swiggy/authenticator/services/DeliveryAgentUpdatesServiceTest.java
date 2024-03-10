package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.DeliveryAgentUpdateDto;
import com.swiggy.authenticator.entities.DeliveryAgent;
import com.swiggy.authenticator.entities.DeliveryAgentUpdate;
import com.swiggy.authenticator.enums.DeliveryAgentStatus;
import com.swiggy.authenticator.enums.DeliveryAgentUpdateType;
import com.swiggy.authenticator.repositories.DeliveryAgentUpdatesDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;

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

    @Spy
    private final DeliveryAgent testDeliveryAgent = new DeliveryAgent(TEST_DELIVERY_AGENT_USERNAME, TEST_DELIVERY_AGENT_PASSWORD, TEST_DELIVERY_AGENT_PINCODE);

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }

    @Test
    public void test_shouldCreateAnUpdateOfLocationType(){
        doNothing().doCallRealMethod().when(this.testDeliveryAgent).setCurrentLocationPincode(anyInt());
        DeliveryAgentUpdate update = new DeliveryAgentUpdate(this.testDeliveryAgent, DeliveryAgentUpdateType.LOCATION, TEST_DELIVERY_AGENT_PINCODE+1);
        when(this.mockedDeliveryAgentUpdatesDao.save(any(DeliveryAgentUpdate.class))).thenReturn(update);
        when(this.mockedDeliveryAgentsService.fetch(TEST_DELIVERY_AGENT_ID)).thenReturn(this.testDeliveryAgent);
        DeliveryAgentUpdateDto dto = new DeliveryAgentUpdateDto(Optional.of(TEST_DELIVERY_AGENT_PINCODE+1), Optional.empty());

        assertDoesNotThrow(()->{
            DeliveryAgentUpdate savedUpdate = this.deliveryAgentUpdatesService.create(TEST_DELIVERY_AGENT_ID, DeliveryAgentUpdateType.LOCATION, dto);
            assertEquals(this.testDeliveryAgent, savedUpdate.getDeliveryAgent());
            assertEquals(String.valueOf(TEST_DELIVERY_AGENT_PINCODE), savedUpdate.getPreviousValue());
            assertEquals(String.valueOf(TEST_DELIVERY_AGENT_PINCODE+1), savedUpdate.getUpdatedValue());
        });
        verify(this.mockedDeliveryAgentUpdatesDao, times(1)).save(any(DeliveryAgentUpdate.class));
        verify(this.mockedDeliveryAgentsService, times(1)).fetch(TEST_DELIVERY_AGENT_ID);
        verify(this.testDeliveryAgent, times(2)).setCurrentLocationPincode(TEST_DELIVERY_AGENT_PINCODE+1);
        verify(this.testDeliveryAgent, never()).setStatus(any(DeliveryAgentStatus.class));

    }

    @Test
    public void test_shouldCreateAnUpdateOfStatusType(){
        doNothing().doCallRealMethod().when(this.testDeliveryAgent).setStatus(any(DeliveryAgentStatus.class));
        DeliveryAgentUpdate update = new DeliveryAgentUpdate(this.testDeliveryAgent, DeliveryAgentUpdateType.STATUS, DeliveryAgentStatus.ENGAGED);
        when(this.mockedDeliveryAgentUpdatesDao.save(any(DeliveryAgentUpdate.class))).thenReturn(update);
        when(this.mockedDeliveryAgentsService.fetch(TEST_DELIVERY_AGENT_ID)).thenReturn(this.testDeliveryAgent);
        DeliveryAgentUpdateDto dto = new DeliveryAgentUpdateDto(Optional.empty(), Optional.of(DeliveryAgentStatus.ENGAGED.name()));

        assertDoesNotThrow(()->{
            DeliveryAgentUpdate savedUpdate = this.deliveryAgentUpdatesService.create(TEST_DELIVERY_AGENT_ID, DeliveryAgentUpdateType.STATUS, dto);
            assertEquals(this.testDeliveryAgent, savedUpdate.getDeliveryAgent());
            assertEquals(DeliveryAgentStatus.AVAILABLE.name(), savedUpdate.getPreviousValue());
            assertEquals(DeliveryAgentStatus.ENGAGED.name(), savedUpdate.getUpdatedValue());
        });
        verify(this.mockedDeliveryAgentUpdatesDao, times(1)).save(any(DeliveryAgentUpdate.class));
        verify(this.mockedDeliveryAgentsService, times(1)).fetch(TEST_DELIVERY_AGENT_ID);
        verify(this.testDeliveryAgent, times(2)).setStatus(DeliveryAgentStatus.ENGAGED);
        verify(this.testDeliveryAgent, never()).setCurrentLocationPincode(TEST_DELIVERY_AGENT_PINCODE+1);
    }
}
