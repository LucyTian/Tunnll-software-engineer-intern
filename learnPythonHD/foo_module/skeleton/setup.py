try:
    from setuptools import setup
except ImportError:
    from distutils.core import setup

config = {
    'description': 'foo_module',
    'author': 'Lu Tian',
    'url': 'URL to get it at',
    'download_url': 'where to download it',
    'author_email': 'My email',
    'version': '0.1',
    'install_requires': ['nose'],
    'packages': ['foo'],
    'scripts': [],
    'name': 'foo'
}

setup (**config)
