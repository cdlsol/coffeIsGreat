from flask import request, jsonify, current_app
from functools import wraps
import logging

logging.basicConfig(level=logging.DEBUG)

def require_api_key(f):
    @wraps(f)
    def wrapper(*args, **kwargs):
        auth_header = request.headers.get("Authorization", "")
        if not auth_header.startswith("Bearer ") or auth_header.split("Bearer ")[1] != current_app.config["API_KEY"]:
            return jsonify({"error": "Unauthorized"}), 401
        
        # No need to create engine or session here; Flask-SQLAlchemy handles it
        logging.debug("API key validated, proceeding with request")
        return f(*args, **kwargs)
    return wrapper

# No teardown needed; Flask-SQLAlchemy manages session lifecycle