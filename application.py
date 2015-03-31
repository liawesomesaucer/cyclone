import os
import tornado.web

from sqlalchemy.orm import scoped_session, sessionmaker
from models import *

import json
testUser = {
	"name" : "Jarnin",
	"email" : "jfang@jfang.edu",
	"password" : "123",
	"description" : "Stuff stuff stuff",
	"profile_pic" : "http://upload.wikimedia.org/wikipedia/commons/thumb/8/85/Smiley.svg/2000px-Smiley.svg.png",
	"interests" : "Eating Badminton Gymming",
	"classes" : "CSE20 CSE30",
	"yesUsers" : "",
	"noUsers" : "",
	"rating" : "4.9",
	"rating_times" : "10",
	"location" : "temporaryvariable"
}

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
			self.write(json.dumps(testUser))
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

class LoginTestHandler(BaseHandler):
	def get(self, email, password):
		try:
			self.write("try to login here")
		except:
			print("failed2")
			raise

class LoginOnlyHandler:
	def get(self):
		if not self.current_user:
			self.redirect("/")
		try:
			self.write("congrats you are a registered user")
		except:
			print("failed")
			raise


class Application(tornado.web.Application):
	def __init__(self):
		
		handlers = [
			(r"/", 				HelloHandler),
			(r"/test/([^/]+)", 	TestHandler),
			(r"/login", 		LoginTestHandler),
			(r"/loggedin",		LoginOnlyHandler)
		]

		self.db = scoped_session(sessionmaker(bind=engine))

		tornado.web.Application.__init__(self, handlers)
