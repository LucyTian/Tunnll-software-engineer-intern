import web
urls = (
    '/','index'
)

app = web.application(urls, globals())
# the font and size is under the template directory
render = web.template.render('templates/')
class index(object):
    def GET(self):
        greeting = "Hello World"
        return render.index(greeting)

if __name__ == "__main__":
    app.run()
