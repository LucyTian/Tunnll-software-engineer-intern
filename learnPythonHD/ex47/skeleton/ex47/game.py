class Room(object):
    
    def __init__(self, name, description):
        self.name = name
        self.description = description
        self.paths={}

    def go(self, direction):
        # if key not available, return default
        return self.paths.get(direction,None) 

    def add_paths(self, paths):
        # update add the paths into paths, note that parameter here is a dict
        self.paths.update(paths)
