import web
urls = (
    '/hello','Index'
)

app = web.application(urls, globals())
# the font and size is under the template directory
render = web.template.render('templates/')
class Index(object):
    form = web.input(name="Nobody")
    def GET(self):
        greeting = "Hello, %s" % form,name
        #return render.index(greeting)
        return render.index(greeting = greeting)

if __name__ == "__main__":
    app.run()
