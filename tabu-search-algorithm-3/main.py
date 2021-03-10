
import logging
logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)
logger.debug('This message should go to the log file')
logger.info('So should this')
logger.warning('And this, too')
logger.error('And non-ASCII stuff, too, like Øresund and Malmö')