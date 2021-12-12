/*
 * Copyright 2016 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.fabricmc.loader.impl.discovery;

import java.io.IOException;
import java.nio.file.Files;

import net.fabricmc.loader.impl.FabricLoaderImpl;

public class ShadedModCandidateFinder implements ModCandidateFinder {
	@Override
	public void findCandidates(ModCandidateConsumer out) {
		/* try (JarInputStream jar = new JarInputStream(
				FabricLoaderImpl.InitHelper.get().getJarPath().toUri().toURL().openStream())) {
			JarEntry entry;

			while ((entry = jar.getNextJarEntry()) != null) {
				String name = entry.getName();
				System.out.print(name);

				if (name.substring(name.lastIndexOf("/") + 1).equals("fabric.mod.json")) {
					System.out.print(" <<<<");
				}

				System.out.println();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} */

		try {
			Files.walk(FabricLoaderImpl.InitHelper.get().getJarPath())
				.filter((p) -> p.getName(p.getNameCount() - 1).toString().equals("fabric.mod.json"))
				.skip(1)
				.forEach((p) -> {
					out.accept(p.getParent(), false);
				});
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
