package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  TorpedoStore primaryTorpedoStore;
  TorpedoStore secondaryTorpedoStore;

  @BeforeEach
  public void init(){
    primaryTorpedoStore = mock(TorpedoStore.class);
    secondaryTorpedoStore = mock(TorpedoStore.class);
    this.ship = new GT4500(primaryTorpedoStore, secondaryTorpedoStore);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);

    verify(primaryTorpedoStore).fire(1);
    verify(secondaryTorpedoStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success(){
    // Arrange
    when(primaryTorpedoStore.fire(1)).thenReturn(true);
    when(secondaryTorpedoStore.fire(1)).thenReturn(true);
    when(primaryTorpedoStore.isEmpty()).thenReturn(false);
    when(secondaryTorpedoStore.isEmpty()).thenReturn(false);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(primaryTorpedoStore).fire(1);
    verify(secondaryTorpedoStore).fire(1);

  }

  @Test
  public void fireTorpedo_Single_FiresPrimaryWhenSecondaryIsEmpty() {
      // Arrange
      when(primaryTorpedoStore.isEmpty()).thenReturn(false);
      when(secondaryTorpedoStore.isEmpty()).thenReturn(true);
      when(primaryTorpedoStore.fire(1)).thenReturn(true);

      // Act
      boolean result = ship.fireTorpedo(FiringMode.SINGLE);

      // Assert
      assertTrue(result);
      verify(primaryTorpedoStore, times(1)).fire(1);
      verify(secondaryTorpedoStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_FiresSecondaryWhenPrimaryIsEmpty() {
      // Arrange
      when(primaryTorpedoStore.isEmpty()).thenReturn(true);
      when(secondaryTorpedoStore.isEmpty()).thenReturn(false);
      when(secondaryTorpedoStore.fire(1)).thenReturn(true);

      // Act
      boolean result = ship.fireTorpedo(FiringMode.SINGLE);

      // Assert
      assertTrue(result);
      verify(secondaryTorpedoStore, times(1)).fire(1);
      verify(primaryTorpedoStore, never()).fire(1);
  }

  @Test
  public void fireTorpedo_Single_WhenBothEmpty() {
      // Arrange
      when(primaryTorpedoStore.isEmpty()).thenReturn(true);
      when(secondaryTorpedoStore.isEmpty()).thenReturn(true);

      // Act
      boolean result = ship.fireTorpedo(FiringMode.SINGLE);

      // Assert
      assertFalse(result);
      verify(primaryTorpedoStore, never()).fire(1);
      verify(secondaryTorpedoStore, never()).fire(1);
  }

    @Test
    public void fireTorpedo_All_WhenPrimaryIsEmpty() {
        // Arrange
        when(primaryTorpedoStore.isEmpty()).thenReturn(true);
        when(secondaryTorpedoStore.isEmpty()).thenReturn(false);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertFalse(result);
        verify(primaryTorpedoStore, never()).fire(1);
        verify(secondaryTorpedoStore, never()).fire(1);
    }

    @Test
    public void fireTorpedo_All_WhenSecondaryIsEmpty() {
        // Arrange
        when(primaryTorpedoStore.isEmpty()).thenReturn(false);
        when(secondaryTorpedoStore.isEmpty()).thenReturn(true);

        // Act
        boolean result = ship.fireTorpedo(FiringMode.ALL);

        // Assert
        assertFalse(result);
        verify(primaryTorpedoStore, never()).fire(1);
        verify(secondaryTorpedoStore, never()).fire(1);
    }
}


