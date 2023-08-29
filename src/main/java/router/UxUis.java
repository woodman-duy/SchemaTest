package router;

import org.skyve.metadata.router.UxUi;

// Only change theme attributes here.
// Declare new UxUis in the DefaultUxUiSelector.
public class UxUis {
	public static final UxUi PHONE = UxUi.newPrimeFaces("phone", "ecuador", "ecuador", "blue");
	public static final UxUi TABLET = UxUi.newPrimeFaces("tablet", "ecuador", "ecuador", "blue");
	public static final UxUi DESKTOP = UxUi.newSmartClient("desktop", "Tahoe", "casablanca");
	public static final UxUi EXTERNAL = UxUi.newPrimeFaces("external", "ecuador", "ecuador", "blue");
	public static final UxUi STARTUP = UxUi.newPrimeFaces("startup", "ecuador", "ecuador", "blue");
}
