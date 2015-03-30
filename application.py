"""This page imports all the handlers and stuff and sets all the routes"""

import os
import tornado.web

class BaseHandler(tornado.web.RequestHandler):
	def set_default_headers(self):
		self.set_header("Access-Control-Allow-Origin", 
			"http://liawesomesaucer.github.io")

from handlers.storyhandler import StoryHandler

# This is a test for hello world
class HelloHandler(BaseHandler):
	def get(self):
		try:
			self.write("Hello world")
		except:
			print("Hello failed")
			raise

class TestHandler(BaseHandler):
	def get(self, test):
		try:
			self.write("this is test %s" % test)
		except:
			print("Test failed")
			raise

class Application(tornado.web.Application):
	def __init__(self):
		
		handlers = [
			(r"/", 				HelloHandler),
			(r"/test/([^/]+)", 	TestHandler),
			(r"/story/([^/]+)", StoryHandler)
		]

		tornado.web.Application.__init__(self, handlers)
