from nose.tools import *
from ex48.parser import Sentence

def test_sentence():
    mySentence = Sentence('player','run','north')
    empty = Sentence('none','none','none')
    empty = empty.parse_sentence([('verb','run'),('direction','north')])
    assert_equal(empty,mySentence)
