import web
urls = (
    '/hello','Index'
)

app = web.application(urls, globals())
# the font and size is under the template directory
render = web.template.render('templates/')
class Index(object):
    def GET(self):
        form = web.input(name="Nobody",greet = None)
        if form.greet:
            greeting = "%s, %s" % (form.greet,form.name)
            #http://localhost:8080/hello?name=Frank&greet=Hola This url would work
            #return render.index(greeting)
            return render.index(greeting = greeting)
        else:
            return "ERROR: greet is required."
if __name__ == "__main__":
    app.run()
