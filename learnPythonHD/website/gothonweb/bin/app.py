import web

from gothonweb import map

urls = (
    '/game','GameEngine',
    '/','Index',
)

app = web.application(urls, globals())

# little hack so that debug mode works with sessions
if web.config.get('_session') is None:
    store = web.session.DiskStore('sessions')
    session = web.session.Session(app,store,initializer={'room':None})
    web.config._session = session
else:
    session = web.config._session


# the font and size is under the template directory
render = web.template.render('templates/',base = "layout")
class Index(object):
    def GET(self):
        # this is used to "setup" the session with starting values
        session.room = map.START
        web.seeother("/game")
"""
    def POST(self):
        form = web.input(name="Nobody",greet="Hello")
        greeting = "%s, %s" % (form.greet,form.name)
            #http://localhost:8080/hello?name=Frank&greet=Hola This url would work
            #return render.index(greeting)
        return render.index(greeting = greeting)
"""
class  GameEngine(object):
    def GET(self):
        if session.room:
            return render.show_room(room=session.room)
        else:
            # why is there here? do you need it?
            return render.you_die()

    def POST(self):
        form = web.input(action=None)

        if session.room and form.action:
            session.room = session.room.go(form.action)

        web.seeother("/game")


if __name__ == "__main__":
    app.run()
