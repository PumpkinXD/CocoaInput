package jp.axer.cocoainput;

import com.sun.jna.Platform;
import jp.axer.cocoainput.arch.darwin.DarwinController;
import jp.axer.cocoainput.arch.dummy.DummyController;
import jp.axer.cocoainput.arch.win.WinController;
import jp.axer.cocoainput.arch.x11.X11Controller;
import jp.axer.cocoainput.plugin.CocoaInputController;
import jp.axer.cocoainput.util.ConfigPack;
import jp.axer.cocoainput.util.ModDetector;
import jp.axer.cocoainput.util.ModLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.apache.commons.io.IOUtils;
import org.lwjgl.glfw.GLFW;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public final class CocoaInput {
    public static final String MOD_ID = "cocoainput";
    public static ConfigPack config = ConfigPack.defaultConfig;
    public static ModDetector modDetector;
    private static CocoaInputController controller;
    private static String zipsource;

    public static void init(ModDetector modDetector) {

        CocoaInput.modDetector =modDetector;
        zipsource = null;//TODO

        try {
            if (Platform.isMac()) {
                CocoaInput.applyController(new DarwinController());
            } else if (Platform.isWindows()) {
                CocoaInput.applyController(new WinController());
            } else if (Platform.isX11()) {
                //Platform.isX11() always return true even on wayland
                if (
                        (
                                CocoaInput.modDetector.isModLoaded("waygl") ||
                                        CocoaInput.modDetector.isModLoaded("wayland-fixes") ||
                                        CocoaInput.modDetector.isModLoaded("waylandfix")
                                /* || patched_GLFW_detection() */
                        ) && GLFW.glfwGetPlatform() == GLFW.GLFW_PLATFORM_WAYLAND
                                && GLFW.glfwPlatformSupported(GLFW.GLFW_PLATFORM_WAYLAND)

                ) {
                    ModLogger.log("CocoaInput is not supported on Wayland yet.");
                    CocoaInput.applyController(new DummyController());
                } else if (Platform.isAndroid()) {//actually it won't work on projav, just use this as a placeholder
                    ModLogger.log("CocoaInput is useless on Android.");
                    CocoaInput.applyController(new DummyController());
                } else {//well... if you are using something else that isn't x11, well...
                    CocoaInput.applyController(new X11Controller());
                }


            } else {
                ModLogger.log("CocoaInput cannot find appropriate Controller in running OS.");
                CocoaInput.applyController(new DummyController());
            }
            ModLogger.log("CocoaInput has been initialized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Write common init code here.
    }

    public static double getScreenScaledFactor() {
        return Minecraft.getInstance().getWindow().getGuiScale();
    }

    public static void applyController(CocoaInputController controller) throws IOException {
        CocoaInput.controller = controller;
        ModLogger.log("CocoaInput is now using controller:" + controller.getClass().toString());
    }

    public static CocoaInputController getController() {
        return CocoaInput.controller;
    }

    public static void copyLibrary(String libraryName, String libraryPath) throws IOException {
        InputStream libFile;
        if (zipsource == null) {//Fabric case
            libFile = CocoaInput.class.getResourceAsStream("/" + libraryPath);
        } else {
            try {//Modファイルを検出し、jar内からライブラリを取り出す
                ZipFile jarfile = new ZipFile(CocoaInput.zipsource);
                libFile = jarfile.getInputStream(new ZipEntry(libraryPath));
            } catch (FileNotFoundException e) {//存在しない場合はデバッグモードであるのでクラスパスからライブラリを取り出す
                ModLogger.log("Couldn't get library path. Is this debug mode?'");
                libFile = ClassLoader.getSystemResourceAsStream(libraryPath);
            }
        }
        Minecraft mc = Minecraft.getInstance();
        String nativeDirString = mc.gameDirectory.getAbsolutePath().concat("/native");
        File nativeDir = new File(nativeDirString);
        File copyLibFile = new File(nativeDirString.concat("/" + libraryName));
        try {
            nativeDir.mkdir();
            FileOutputStream fos = new FileOutputStream(copyLibFile);
            copyLibFile.createNewFile();
            IOUtils.copy(libFile, fos);
            fos.close();
        } catch (IOException e1) {
            ModLogger.error("Attempted to copy library to ./native/" + libraryName + " but failed.");
            throw e1;
        }
        System.setProperty("jna.library.path", nativeDir.getAbsolutePath());
        ModLogger.log("CocoaInput has copied library to native directory.");
    }

    public void distributeScreen(Screen sc) {
        if (CocoaInput.getController() != null) {
            CocoaInput.getController().screenOpenNotify(sc);
        }
    }

}
