import os
import tornado.web

from sqlalchemy.orm import scoped_session, sessionmaker
from models import *

class BaseHandler(tornado.web.RequestHandler):
	def set_default_headers(self):
		def db(self):
			return self.application.db

		# Get the current user from the db
		def get_current_user(self):
			user_id = self.get_secure_cookie("user")
			if not user_id: 
				return None
			return self.db.query(User).get(user_id)


		self.set_header("Access-Control-Allow-Origin", 
			"http://liawesomesaucer.github.io")

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
		if not self.current_user:
			self.redirect("/")
		try:
			self.write("this is test %s" % test)
		except:
			print("Test failed")
			raise

class Application(tornado.web.Application):
	def __init__(self):
		
		handlers = [
			(r"/", 				HelloHandler),
			(r"/test/([^/]+)", 	TestHandler)
		]

		self.db = scoped_session(sessionmaker(bind=engine))

		tornado.web.Application.__init__(self, handlers)
