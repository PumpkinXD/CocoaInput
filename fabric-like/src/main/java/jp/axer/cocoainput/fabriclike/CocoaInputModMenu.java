package jp.axer.cocoainput.fabriclike;


import com.terraformersmc.modmenu.api.ModMenuApi;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import jp.axer.cocoainput.util.FCConfig;

public class CocoaInputModMenu implements ModMenuApi {

	@Override
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return parent -> (new FCConfig().getScreen(parent));
	}
}
