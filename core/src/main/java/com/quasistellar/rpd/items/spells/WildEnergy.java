/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.quasistellar.rpd.items.spells;

import com.quasistellar.rpd.actors.buffs.ArtifactRecharge;
import com.quasistellar.rpd.actors.buffs.Buff;
import com.quasistellar.rpd.actors.buffs.Recharging;
import com.quasistellar.rpd.actors.hero.Hero;
import com.quasistellar.rpd.items.artifacts.Artifact;
import com.quasistellar.rpd.items.quest.MetalShard;
import com.quasistellar.rpd.items.scrolls.ScrollOfRecharging;
import com.quasistellar.rpd.items.scrolls.exotic.ScrollOfMysticalEnergy;
import com.quasistellar.rpd.items.wands.CursedWand;
import com.quasistellar.rpd.mechanics.Ballistica;
import com.quasistellar.rpd.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;

public class WildEnergy extends TargetedSpell {
	
	{
		image = ItemSpriteSheet.WILD_ENERGY;
	}
	
	//we rely on cursedWand to do fx instead
	@Override
	protected void fx(Ballistica bolt, Callback callback) {
		affectTarget(bolt, curUser);
	}
	
	@Override
	protected void affectTarget(Ballistica bolt, final Hero hero) {
		CursedWand.cursedZap(this, hero, bolt, new Callback() {
			@Override
			public void call() {
				ScrollOfRecharging.charge(hero);

				hero.belongings.charge(1f);
				for (int i = 0; i < 4; i++){
					if (hero.belongings.misc1 instanceof Artifact) ((Artifact) hero.belongings.misc1).charge(hero);
					if (hero.belongings.misc2 instanceof Artifact) ((Artifact) hero.belongings.misc2).charge(hero);
				}

				Buff.affect(hero, Recharging.class, 8f);
				Buff.affect(hero, ArtifactRecharge.class).prolong( 8 );
				
				detach( curUser.belongings.backpack );
				updateQuickslot();
				curUser.spendAndNext( 1f );
			}
		});
	}
	
	@Override
	public int price() {
		//prices of ingredients, divided by output quantity
		return Math.round(quantity * ((50 + 100) / 5f));
	}
	
	public static class Recipe extends com.quasistellar.rpd.items.Recipe.SimpleRecipe {
		
		{
			inputs =  new Class[]{ScrollOfMysticalEnergy.class, MetalShard.class};
			inQuantity = new int[]{1, 1};

			cost = 8;
			
			output = WildEnergy.class;
			outQuantity = 5;
		}
		
	}
}
