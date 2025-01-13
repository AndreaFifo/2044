package io.github.videogame;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import io.github.videogame.controller.SettingsController;
import io.github.videogame.view.screens.MySlider;
import io.github.videogame.view.screens.SettingsScreen;
import io.github.videogame.model.Gioco;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

//Test di configurazione: Entrambi i test verificano che quando il metodo setup() del SettingsController viene chiamato,
// gli slider della musica e del suono vengano configurati correttamente (cioè il loro valore venga impostato su 1).

//Test di aggiunta dei listener: Oltre a verificare la configurazione del valore degli slider,
// i test assicurano anche che vengano aggiunti i listener agli slider, che sono necessari per gestire eventuali cambiamenti di stato
// quando l'utente interagisce con gli slider.

public class SettingsControllerTest {

    @Mock
    private Gioco mockGame;
    @Mock
    private SettingsScreen mockView;

    // Se SettingsScreen restituisce un tipo personalizzato, usa quello invece di Slider
    @Mock
    private MySlider mockMusicSlider;
    @Mock
    private MySlider mockSoundSlider;
    @Mock
    private CheckBox mockVSyncCheckBox;
    @Mock
    private TextButton mockBackButton;


    private SettingsController settingsController;

    @Before
    public void setUp() {
        Gdx.app = mock(Application.class);

        // Mock delle preferenze
        Preferences mockPreferences = mock(Preferences.class);
        when(Gdx.app.getPreferences("game_settings")).thenReturn(mockPreferences);

        // Inizializza i mock
        MockitoAnnotations.initMocks(this);

        // Prepara i comportamenti simulati per i metodi dei mock
        when(mockView.getMusicSlider()).thenReturn(mockMusicSlider);
        when(mockView.getSoundSlider()).thenReturn(mockSoundSlider);
        when(mockView.getvSyncCheckBox()).thenReturn(mockVSyncCheckBox);
        when(mockView.getBackButton()).thenReturn(mockBackButton);

        // Crea un'istanza di SettingsController con i mock
        settingsController = new SettingsController(mockGame, mockView);
    }

    @Test
    public void testSetupMusicSlider() {
        // Esegui il setup
        settingsController.setupValues();

        // Verifica che il valore del music slider sia impostato a 1
        verify(mockMusicSlider).setValue(1);

        // Verifica che il listener venga aggiunto (ma non eseguito)
        verify(mockMusicSlider).addListener(any(ChangeListener.class));
    }

    @Test
    public void testSetupSoundSlider() {
        // Esegui il setup
        settingsController.setupValues();

        // Verifica che il valore del sound slider sia impostato a 1
        verify(mockSoundSlider).setValue(1);

        // Verifica che il listener venga aggiunto (ma non eseguito)
        verify(mockSoundSlider).addListener(any(ChangeListener.class));
    }

    @Test
    public void testSetupVSyncCheckBox() {
        // Esegui il setup
        settingsController.setupValues();

        // Verifica che il valore del checkbox sia impostato a false
        verify(mockVSyncCheckBox).setChecked(false);

        // Verifica che il listener venga aggiunto (ma non eseguito)
        verify(mockVSyncCheckBox).addListener(any(ChangeListener.class));
    }
}
