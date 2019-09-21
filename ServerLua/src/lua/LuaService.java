package lua;

import cn.iolove.luajava.LuaState;
import cn.iolove.luajava.LuaStateFactory;

public class LuaService {
	private static LuaState mLuaState = LuaStateFactory.newLuaState();
	static{
		mLuaState.openLibs();

	}
	public static LuaState getLuaState()
	{
		return mLuaState;
	}

}
