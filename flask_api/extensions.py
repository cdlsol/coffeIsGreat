from flask_limiter import Limiter
from flask_sqlalchemy import SQLAlchemy

db = SQLAlchemy()
limiter = Limiter(key_func=lambda: "global")  # Example: can be customized based on IP or user