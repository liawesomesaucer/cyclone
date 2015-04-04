LARGE_COLUMN_SIZE = 5000

from sqlalchemy import create_engine
from sqlalchemy import Column, Integer, String, Float, Boolean
from utils import *
import utils

from sqlalchemy.ext.declarative import declarative_base

Base = declarative_base()

# Default User class
# class User(Base):
# 	__tablename__  = 'users'

# 	name = 			Column(String(50))
# 	email = 		Column(String(200), primary_key=True)
# 	password = 		Column(String(50))
# 	description = 	Column(String(400))	# "bio"
# 	profile_pic = 	Column(String(400))	# stores url to image
# 	interests = 	Column(String(LARGE_COLUMN_SIZE))
# 	classes = 		Column(String(LARGE_COLUMN_SIZE))
# 	yesUsers = 		Column(String(LARGE_COLUMN_SIZE))
# 	noUsers = 		Column(String(LARGE_COLUMN_SIZE))
# 	rating = 		Column(Float())
# 	rating_times = 	Column(Integer)
# 	location = 		Column(Float())

# 	def __init__ (self, name, email, password, description=None,
# 		profile_pic=None, interests=None, classes=None, yesUsers=None, noUsers=None, 
# 		rating=0, rating_times=0, location=0):

# 		self.name = 		name
# 		self.email = 		email
# 		self.password = 	password
# 		self.description = 	description
# 		self.profile_pic = 	profile_pic
# 		self.interests = 	list_to_string(interests)
# 		self.classes = 		list_to_string(classes)
# 		self.yesUsers = 	list_to_string(yesUsers)
# 		self.noUsers = 		list_to_string(noUsers)
# 		self.rating = 		rating
# 		self.location = 	location

# 	def __repr__(self):
# 		return "<User %s>" % self.email


class User( Base ):
	__tablename__ = 'users'

	# Total of 11 fields

	# Default, important user fields
	email = Column(String(100), primary_key=True)
	name = Column(String(50))
	password = Column(String(100))

	# Personal info fields
	bio = Column(String(200))	# Also where tutors store credentials
	image = Column(String(200))	# Uri for image

	# If this guy is a tutor
	tutor = Column(Boolean())
	tutor_availability = Column(Boolean())	# If tutor is available for tutoring
	tutor_rating = Column(Float())
	tutor_fields = Column(String(5000))
	tutor_price = Column(Float())	# Tutor's desired price per hour

	# Optional fields
	location = Column(String(20))	# Location, not sure if necessary

	def __init__ (	self, email, password, name, bio=None, image=None, tutor=False,
					tutor_availability=False, tutor_rating=0.0, tutor_fields="",
					tutor_price=0.0, location="" ):

		self.email = email
		self.password = password
		self.name = name
		self.bio = bio
		self.image = image
		self.tutor = tutor
		self.tutor_availability = tutor_availability
		self.tutor_rating = tutor_rating
		self.tutor_fields = tutor_fields
		self.tutor_price = tutor_price
		self.location = location

	def __repr__ ( self ):
		if self.tutor:			
			return "<Tutor %s>" % name
		return "<User %s>" % name