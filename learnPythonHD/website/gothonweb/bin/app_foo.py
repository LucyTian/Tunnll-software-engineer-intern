import web
urls = (
    '/','foo'
)

app = web.application(urls, globals())
# the font and size is under the template directory
render = web.template.render('templates/')
class foo(object):
    def GET(self):
        greeting = "Hello World"
        return render.foo(greeting)

if __name__ == "__main__":
    app.run()
