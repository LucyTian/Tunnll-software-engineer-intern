def scan(stuff):
    # group all the inputs into list 
    direction = ['north','south','east','west','down','up',
            'left','right','back']
    verb = ['go','stop','kill','eat']
    stop = ['the','in','of','from','at','it']
    noun = ['door','bear','princess','cabinet']
    
    # define helper function to judge if int
    def is_Int(word):
        try:
            return int(word)
        except ValueError:
            return None

    #stuff = raw_input('> ')
    words = stuff.split()
    
    result = []

    # determine the group of every word, if not in any, group to error
    for word in words:
        
        if word in direction:
            result.append(('direction',word))
        elif word in verb:
            result.append(('verb',word))
        elif word in stop:
            result.append(('stop',word))
        elif word in noun:
            result.append(('noun',word))
        elif is_Int(word) != None:
            result.append(('number',int(word)))
        else:
            result.append(('error',word))

    return result
