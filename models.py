LARGE_COLUMN_SIZE = 5000

from sqlalchemy import create_engine
from sqlalchemy import Column, Integer, String, Float
from utils import *
import utils

from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

# Default User class
class User(Base):
	__tablename__  = 'users'

	name = 			Column(String(50))
	email = 		Column(String(200), primary_key=True)
	password = 		Column(String(50))
	description = 	Column(String(400))	# "bio"
	profile_pic = 	Column(String(400))	# stores url to image
	interests = 	Column(String(LARGE_COLUMN_SIZE))
	classes = 		Column(String(LARGE_COLUMN_SIZE))
	yesUsers = 		Column(String(LARGE_COLUMN_SIZE))
	noUsers = 		Column(String(LARGE_COLUMN_SIZE))
	rating = 		Column(Float())
	rating_times = 	Column(Integer)
	location = 		Column(Float())

	def __init__ (self, name, email, password, description=None,
		profile_pic=None, interests=None, classes=None, yesUsers=None, noUsers=None, 
		rating=0, rating_times=0, location=0):

		self.name = 		name
		self.email = 		email
		self.password = 	password
		self.description = 	description
		self.profile_pic = 	profile_pic
		self.interests = 	list_to_string(interests)
		self.classes = 		list_to_string(classes)
		self.yesUsers = 	list_to_string(yesUsers)
		self.noUsers = 		list_to_string(noUsers)
		self.rating = 		rating
		self.location = 	location

	def __repr__(self):
		return "<User %s>" % self.email