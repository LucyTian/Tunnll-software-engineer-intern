from sys import exit
from random import randint

class Scene(object):
    def enter(self):
        print "This scene is not yet configured. Subclass it and implement enter()"
        exit(1)

class Engine(object):
    def __init__(self, scene_map):
        self.scene_map = scene_map

    def play(self):
        current_scene = self.scene_map.opening_scene()
        last_scene = self.scene_map.next_scene('finished')
        
        while current_scene != last_scene:
            next_scene_name = current_scene.enter()
            current_scene = self.scene_map.next_scene(next_scene_name)
        
        current_scene.enter()

class Death(Scene):
    quips = [
        "You died. You kinda suck at this.",
        "I have a small puppy that's better at this.",
        "You such a luser."
    ]

    def enter(self):
        # can I omit the Death class name here?
        print Death.quips[randint(0,len(self.quips)-1)]
        exit(1)

class CentralCorridor(Scene):
    def enter(self):
        #gothon standng there and have to defeat with joke
        print "The Gothon of Planet Percal #25 have invaded your ship and destroyed"
        print "your entire crew. You are the last surviving member and your last"
        print "mission is to get the neutron destruct bomb from the Weapons Armory,"
        print "put it in the bridge, and blow the ship up after getting into an "
        print "escape pod."
        print "\n"
        print "You're running down the central corridor to the Weapins Armory when"
        print "a Gothon jumps out, red scaly skin, dark grimly teeth, and evil clown costume"
        print "flowing around his hate filled body. He's blokcing the door to the"
        print "Armory and about to pull a weapon to blast you."
        
        action = raw_input("Choose your action from \"shoot\", \"dodge\" or \"tell a joke\": ")
        
        if action == "shoot!":
            print "Quick on the draw you yank out your blaster and fire it tat the Gothon."
            print "His clown costume is flowing and moving around his body, which throws"
            print "off your aim. Your laser hits his costume but misses him entirely"
            return 'death'
        
        elif action == "dodge!":
            print "In the middle of you artful dodge your foot slips and you bang you head on the metal"
            print "wall ad pass out"
            return 'death'

        elif action == "tell a joke":
            print "Lucky for you they made you learn Gothon insults in the academy"
            return 'laser_weapon_armory'
        
        else:
            print "DOES NOT COMPUTE!"
            return 'central_corridor'

class LaserWeaponArmory(Scene):
    def enter(self):
        print "The bomb is in a container, there is a keypad lock on the box, you need to enter the"
        print "code to get it out. If you get the code wrong 10 times the lock closes forever"
        print "The lock is 3 digits."
        code = "%d%d%d" % (randint(1,9), randint(1,9), randint(1,9))
        guess = raw_input("[keypad]> ")
        guess_time = 0

        while guess != code and guess_time < 10:
            print "BZZZZZEDD!"
            guess_time += 1
            guess = raw_input("[keypad]> ")

        if guess == code:
            print "You grab the neutron bomb and run to the bridge"
            return 'the_bridge'
        else:
            print "The lock buzzes one last time and you die"
            return 'death'

class TheBridge(Scene):
    def enter(self):
        print "You are on the bridge now, place the bomb"
        action = raw_input ("Enter your action to place the bomb(thow the bomb, slowly place the bomb): ")
        
        if action == "throw the bomb":
            print "You dropped and bomb and it went off"
            return 'death'

        if action == "slowly place the bomb":
            print "placed the bomb successfully"
            return 'escape_pod'
        
        else:
            print "DOES NOT COMPUTE!"
            return 'the_bridge'
class EscapePod(Scene):
    def enter(self):
        print "Now, you have found the escape pod"
        print "There are 5 pods, which one are you taking?"

        good_pod = randint(1,5)
        guess = raw_input("[pod #](enter one number from 1 to 5)> ")

        if int(guess) != good_pod:
            print "Sadly you chose the wrong pod in the last step"
            return 'death'
        else:
            "You take the right escape pod and the Gothon ship explode, you won!"
            
            return 'finished'

class Finished(Scene):
    def enter(self):
        print "You won! Good job."
        return 'finished'


class Map(object):
    
    scenes = {
        'central_corridor' : CentralCorridor(),
        'laser_weapon_armory' : LaserWeaponArmory(),
        'the_bridge' : TheBridge(),
        'escape_pod' : EscapePod(),
        'death': Death(),
        'finished': Finished(),
    }

    def __init__(self, start_scene):
        self.start_scene = start_scene

    def next_scene(self, scene_name):
        val = Map.scenes.get(scene_name)
        return val

    def opening_scene(self):
        return self.next_scene(self.start_scene)


a_map = Map('central_corridor')
a_game = Engine(a_map)
a_game.play()
