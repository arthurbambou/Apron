package io.github.betterthanupdates.apron.stapi;

import net.glasslauncher.hmifabric.tabs.TabRegistry;

public class HMICompat {
	protected static void regenerateRecipeList() {
		TabRegistry.INSTANCE.forEach(tab -> {
			((HMITab) tab).apron$updateRecipeList();
		});
	}
}
