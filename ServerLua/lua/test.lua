require "lua.data"
require "lua.base64"
require "lua.tool"

print (package.path);
local function sum(a, b)
        return a + b
end

function POST_abc(request)
	print(request)
	local file = io.open("init.lua","r")
	local str=""
	if file then
		for line in file:lines() do
		str=str+line
		end
	end


	
	return request+line
end
function GET_api_doujia( request )
	local str=os.execute("echo %date%")
	return str,"200"
	-- body
end
function GET_api_abc(request)
	return GET_doujia_dj(request)
end
function GET_doujia_dj(request,context)
	local data={page='13'}
	local itemsOnPage=1
	local pageTotal=math.floor(ArticleService:getArticlesTotal()/itemsOnPage+0.9)

	data=cjson.decode(request)
	local db


    if (data.page==nil)or (tonumber(data.page)==nil)or(tonumber(data.page)>pageTotal)or(tonumber(data.page)<0 ) then
    db=(ArticleService:getArticles(1,itemsOnPage))

    data.page='1'
	else
	db=ArticleService:getArticles((tonumber(data.page)-1)*itemsOnPage,itemsOnPage)


	end
	db=cjson.decode(db)
	data.items=db.total
    data.itemsOnPage=itemsOnPage
	data.title=('D先生独立博客')

	data.items=to_base64(data.items)
	data.itemsOnPage=to_base64(data.itemsOnPage)
	data.page=to_base64(data.page)
	data.data=db

	print(dumptable(db))

	--local data=to_base64('D先生独立博客')
	--local str="{\"title\":\"D先生独立博客\",\"page\":\"10\"}"
	local str	
	str=cjson.encode(data)
	str=string.gsub(str,"\\","")
	return str,"201"
end

function GET_abcd(request,context)
	print(request)
	local result =  context:checkSession() and "true" or "false"
	local str="国宝亚楠喜欢窦佳"..result
	local id=context:getSession("user","aiyanan",1000)
	local results =  context:checkSession() and "true" or "false"
	str=str..results
	context:dropSession()
	local resultss =  context:checkSession() and "true" or "false"
    str=str..resultss
    str=str.." "..id




	
	sayhello()
	return str,"201"
end
function GET_abcd_lib(  )
	-- body
	return "fdslfdjl"
end