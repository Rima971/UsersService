package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.DeliveryAgentDto;
import com.swiggy.authenticator.entities.DeliveryAgent;
import com.swiggy.authenticator.repositories.DeliveryAgentsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.swiggy.authenticator.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class DeliveryAgentsServiceTest {
    @Mock
    private DeliveryAgentsDao mockedDeliveryAgentsDao;

    @InjectMocks
    private DeliveryAgentsService deliveryAgentsService;

    private final DeliveryAgent testDeliveryAgent = new DeliveryAgent(TEST_DELIVERY_AGENT_USERNAME, TEST_DELIVERY_AGENT_PASSWORD, TEST_DELIVERY_AGENT_PINCODE);

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }

    @Test
    public void test_shouldCreateADeliveryAgent(){
        when(this.mockedDeliveryAgentsDao.save(any(DeliveryAgent.class))).thenReturn(this.testDeliveryAgent);
        DeliveryAgentDto request = new DeliveryAgentDto(TEST_DELIVERY_AGENT_USERNAME, TEST_DELIVERY_AGENT_PASSWORD, TEST_DELIVERY_AGENT_PINCODE);

        assertDoesNotThrow(()->{
            DeliveryAgent savedDeliveryAgent = this.deliveryAgentsService.create(request);
            assertEquals(savedDeliveryAgent, this.testDeliveryAgent);
        });
        verify(this.mockedDeliveryAgentsDao, times(1)).save(any(DeliveryAgent.class));
    }

    @Test
    public void test_shouldFetchAllDeliveryAgentsWhenNoFilterCriteriaGiven(){
        List<DeliveryAgent> customers = new ArrayList<DeliveryAgent>(List.of(this.testDeliveryAgent));
        when(this.mockedDeliveryAgentsDao.findAll()).thenReturn(customers);

        assertDoesNotThrow(()->{
            List<DeliveryAgent> fetchedList = this.deliveryAgentsService.fetchAll(Optional.empty(), Optional.empty());
            assertEquals(customers, fetchedList);
        });
        verify(this.mockedDeliveryAgentsDao, times(1)).findAll();
    }

    @Test
    public void test_shouldFetchAllDeliveryAgentsWithGivenPincode(){
        List<DeliveryAgent> deliveryAgents = new ArrayList<DeliveryAgent>(List.of(this.testDeliveryAgent));
        when(this.mockedDeliveryAgentsDao.findALlByCurrentLocationPincode(TEST_DELIVERY_AGENT_PINCODE)).thenReturn(deliveryAgents);

        assertDoesNotThrow(()->{
            List<DeliveryAgent> fetchedList = this.deliveryAgentsService.fetchAll(Optional.of(TEST_DELIVERY_AGENT_PINCODE), Optional.empty());
            assertEquals(deliveryAgents, fetchedList);
        });
        verify(this.mockedDeliveryAgentsDao, times(1)).findALlByCurrentLocationPincode(TEST_DELIVERY_AGENT_PINCODE);
    }
}
