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

package com.quasistellar.rpd.windows;

import com.quasistellar.rpd.Challenges;
import com.quasistellar.rpd.Dungeon;
import com.quasistellar.rpd.actors.mobs.npcs.Ghost;
import com.quasistellar.rpd.items.Item;
import com.quasistellar.rpd.messages.Messages;
import com.quasistellar.rpd.scenes.PixelScene;
import com.quasistellar.rpd.sprites.FetidRatSprite;
import com.quasistellar.rpd.sprites.GnollTricksterSprite;
import com.quasistellar.rpd.sprites.GreatCrabSprite;
import com.quasistellar.rpd.ui.RedButton;
import com.quasistellar.rpd.ui.RenderedTextMultiline;
import com.quasistellar.rpd.ui.Window;
import com.quasistellar.rpd.utils.GLog;

public class WndSadGhost extends Window {

	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final float GAP		= 2;
	
	public WndSadGhost( final Ghost ghost, final int type ) {
		
		super();
		
		IconTitle titlebar = new IconTitle();
		RenderedTextMultiline message;
		switch (type){
			case 1:default:
				titlebar.icon( new FetidRatSprite() );
				titlebar.label( Messages.get(this, "rat_title") );
				message = PixelScene.renderMultiline( Messages.get(this, "rat")+Messages.get(this, "give_item"), 6 );
				break;
			case 2:
				titlebar.icon( new GnollTricksterSprite() );
				titlebar.label( Messages.get(this, "gnoll_title") );
				message = PixelScene.renderMultiline( Messages.get(this, "gnoll")+Messages.get(this, "give_item"), 6 );
				break;
			case 3:
				titlebar.icon( new GreatCrabSprite());
				titlebar.label( Messages.get(this, "crab_title") );
				message = PixelScene.renderMultiline( Messages.get(this, "crab")+Messages.get(this, "give_item"), 6 );
				break;

		}

		titlebar.setRect( 0, 0, WIDTH, 0 );
		add( titlebar );

		message.maxWidth(WIDTH);
		message.setPos(0, titlebar.bottom() + GAP);
		add( message );
		
		RedButton btnWeapon = new RedButton( Messages.get(this, "weapon") ) {
			@Override
			protected void onClick() {
				selectReward( ghost, Ghost.Quest.weapon );
			}
		};
		btnWeapon.setRect( 0, message.top() + message.height() + GAP, WIDTH, BTN_HEIGHT );
		add( btnWeapon );

		if (!Dungeon.isChallenged( Challenges.NO_ARMOR )) {
			RedButton btnArmor = new RedButton( Messages.get(this, "armor") ) {
				@Override
				protected void onClick() {
					selectReward(ghost, Ghost.Quest.armor);
				}
			};
			btnArmor.setRect(0, btnWeapon.bottom() + GAP, WIDTH, BTN_HEIGHT);
			add(btnArmor);

			resize(WIDTH, (int) btnArmor.bottom());
		} else {
			resize(WIDTH, (int) btnWeapon.bottom());
		}
	}
	
	private void selectReward( Ghost ghost, Item reward ) {
		
		hide();
		
		if (reward == null) return;
		
		reward.identify();
		if (reward.doPickUp( Dungeon.hero )) {
			GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward.name()) );
		} else {
			Dungeon.level.drop( reward, ghost.pos ).sprite.drop();
		}
		
		ghost.yell( Messages.get(this, "farewell") );
		ghost.die( null );
		
		Ghost.Quest.complete();
	}
}
