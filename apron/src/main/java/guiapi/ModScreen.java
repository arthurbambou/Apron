package guiapi;

import de.matthiasmann.twl.Widget;
import de.matthiasmann.twl.renderer.lwjgl.LWJGLRenderer;
import de.matthiasmann.twl.renderer.lwjgl.RenderScale;
import guiapi.widget.ScreenWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.ScreenScaler;

public class ModScreen extends Screen {
	public static ModScreen currentScreen;
	public int backgroundType = 0;
	public Widget mainwidget;
	public Screen parentScreen;

	public static void back() {
		if (currentScreen != null) {
			Minecraft m = ModSettings.getMcinst();
			m.setScreen(currentScreen.parentScreen);

			if (currentScreen.parentScreen instanceof ModScreen) {
				currentScreen = (ModScreen) currentScreen.parentScreen;
				currentScreen.setActive();
			} else {
				currentScreen = null;
			}
		}
	}

	public static void clicksound() {
		Minecraft m = ModSettings.getMcinst();
		m.soundManager.playSound("random.click", 1.0F, 1.0F);
	}

	public static void show(ModScreen screen) {
		Minecraft m = ModSettings.getMcinst();
		m.setScreen(screen);
		screen.setActive();
	}

	public static void show(Widget screen) {
		show(new ModScreen(currentScreen, screen));
	}

	public ModScreen(Screen screen) {
		this.parentScreen = screen;
		currentScreen = this;
		this.passEvents = false;
	}

	public ModScreen(Screen screen, Widget widget) {
		this.mainwidget = widget;
		this.parentScreen = screen;
		currentScreen = this;
		this.passEvents = false;
	}

	@Override
	public void render(int var1, int var2, float var3) {
		switch (this.backgroundType) {
			case 0:
				this.renderBackground();
				break;
			case 1:
				this.renderBackgroundTexture(0);
		}

		LWJGLRenderer var4 = (LWJGLRenderer) ScreenWidget.getInstance().gui.getRenderer();
		ScreenScaler var5 = new ScreenScaler(
				ScreenWidget.getInstance().minecraftInstance.options,
				ScreenWidget.getInstance().minecraftInstance.displayWidth,
				ScreenWidget.getInstance().minecraftInstance.displayHeight
		);
		RenderScale.scale = var5.scaleFactor;
		var4.syncViewportSize();
		ScreenWidget.getInstance().gui.update();
	}

	@Override
	public void tickInput() {
	}

	private void setActive() {
		ScreenWidget.getInstance().setScreen(this.mainwidget);
	}
}
