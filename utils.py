# Utility methods

# Function to convert list to string
def list_to_string(list_obj):
	return str(list_obj).replace(',','')[1:-1]

# Function to convert string to list
def string_to_list(string_obj):
	return string_obj.split()