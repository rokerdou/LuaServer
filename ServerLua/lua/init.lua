require "lua.test"
require "lua.tool"
function sayhello()
    local a=1
    print(a)

	a=a+1
	print(a)
	print("hello world")
end

function boot()
	-- body
	    Registry:register("GET_doujia_dj","test.lua")
		Registry:register("GET_abcd","test.lua")
		Registry:register("POST_abc","test.lua")
		Registry:register("GET_abcd_lib","test.lua")
		Registry:register("GET_api_abc","test.lua")

	return "hello"
end
function InvalidRequest(request,context)
	return "无效请求","400"
	-- body
end



