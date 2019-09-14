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

package com.quasistellar.rpd.actors.buffs;

import com.quasistellar.rpd.actors.Char;
import com.quasistellar.rpd.actors.hero.Hero;
import com.quasistellar.rpd.actors.hero.HeroClass;
import com.quasistellar.rpd.messages.Messages;
import com.quasistellar.rpd.ui.BuffIndicator;

public class Roots extends FlavourBuff {

	{
		type = buffType.NEGATIVE;
		announced = true;
	}
	
	@Override
	public boolean attachTo( Char target ) {
		if (!target.flying && super.attachTo( target )) {
			if (target instanceof Hero) {
				if (((Hero)target).heroClass == HeroClass.ELF) {
					return false;
				}
			}
			target.rooted = true;
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void detach() {
		target.rooted = false;
		super.detach();
	}
	
	@Override
	public int icon() {
		return BuffIndicator.ROOTS;
	}
	
	@Override
	public String toString() {
		return Messages.get(this, "name");
	}

	@Override
	public String heroMessage() {
		return Messages.get(this, "heromsg");
	}

	@Override
	public String desc() {
		return Messages.get(this, "desc", dispTurns());
	}
}