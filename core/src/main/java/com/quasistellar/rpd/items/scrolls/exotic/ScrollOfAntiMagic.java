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

package com.quasistellar.rpd.items.scrolls.exotic;

import com.quasistellar.rpd.Dungeon;
import com.quasistellar.rpd.actors.buffs.Buff;
import com.quasistellar.rpd.actors.buffs.Invisibility;
import com.quasistellar.rpd.actors.buffs.MagicImmune;
import com.quasistellar.rpd.actors.hero.HeroClass;
import com.quasistellar.rpd.effects.Flare;
import com.quasistellar.rpd.items.scrolls.Scroll;
import com.quasistellar.rpd.messages.Messages;
import com.quasistellar.rpd.utils.GLog;

public class ScrollOfAntiMagic extends ExoticScroll {
	
	{
		initials = 7;
	}
	
	@Override
	public void doRead() {
		Invisibility.dispel();
		
		Buff.affect( curUser, MagicImmune.class, 20f );
		new Flare( 5, 32 ).color( 0xFF0000, true ).show( curUser.sprite, 2f );
		
		setKnown();
		
		readAnimation();
	}
}
