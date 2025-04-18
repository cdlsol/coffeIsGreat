from flask import Blueprint, jsonify, current_app
from flask_api.extensions import db, limiter
from flask_api.models.products import Product
from flask_api.middleware.api_key_auth import require_api_key
from sqlalchemy import distinct

bp = Blueprint('product', __name__)


@bp.route('/products', methods = ['GET'])
@limiter.limit("100 per 30 hour")
@require_api_key

def get_products():
    try:
        current_app.logger.info("Querying product dimension")

        result = (
            db.session.query(Product.product, Product.category, Product.unit_price)
            .distinct()
            .all()
        )

        current_app.logger.info("Product query complete")
        data = [
            {
                'product': row.product,
                'category': row.category,
                'unit_price': row.unit_price
            } for row in result
        ]
        current_app.logger.info("Product data processed")
        return jsonify(data), 200
    except Exception as e:

        return jsonify({"error": str(e)}), 500