#!/usr/bin/env python

import cPickle
import logging
import getopt
import sys
import json
import re

def add_lib_path(lib_path):
  sys.path.append(lib_path)
  logging.info('Added %s to sys.path' % lib_path)
  
def import_server2():
  from branch_utility import BranchUtility
  from commit_tracker import CommitTracker
  from compiled_file_system import CompiledFileSystem
  from data_source_registry import CreateDataSources
  from environment import GetAppVersion
  from environment_wrappers import CreateUrlFetcher, GetAccessToken
  from future import All
  from gcs_file_system_provider import CloudStorageFileSystemProvider
  from host_file_system_provider import HostFileSystemProvider
  from local_git_util import ParseRevision
  from object_store_creator import ObjectStoreCreator
  from persistent_object_store_fake import PersistentObjectStoreFake
  from render_refresher import RenderRefresher
  from server_instance import ServerInstance
  from timer import Timer
  
def default_json_serializer(obj):
    return None

def load_cache(filename):
  pAPIDataSource = re.compile('.*(APIDataSource).*')
  with open(filename, 'r') as f:
    d = cPickle.load(f)
    
  data = None
  for k, v in d.iteritems():
    m = pAPIDataSource.match(str(k))
    if m:
      logging.info('parsing %s (%s)' % (str(k), len(v)))
      data = cPickle.loads(v)

  return data
  
def save_cache(filename, data):
  text = json.dumps(data, sort_keys=True, indent=2, default=default_json_serializer)
  
  with open(filename, 'w') as f:
    f.write(text)
    
  logging.info('Saved %s APIs to "%s".' % (len(data), filename))

def _Main(argv):
  try:
    opts = dict((name[2:], value) for name, value in
                getopt.getopt(argv, '',
                              ['load-file=', 'save-file=', 'server2-dir='])[0])
  except getopt.GetoptError as e:
    print '%s\n' % e
    print (
    'Usage: cache2json.py [options]\n\n'
    'Options:\n'
    '  --server2-dir=DIR         Python module path'
    '  --load-file=FILE          Load object store data from FILE before\n'
    '                            starting the update.\n'
    '  --save-file=FILE          Save object store data to FILE after running\n'
    '                            the update.\n')
    exit(1)

  logging.getLogger().setLevel(logging.INFO)

  server2_dir = opts.get('server2-dir', '../../../chrome/src/chrome/common/extensions/docs/server2')
  load_file = opts.get('load-file', 'apis.cache')
  save_file = opts.get('save-file', 'apis.json')
  
  if server2_dir:
    add_lib_path(server2_dir)
    
  logging.info('Importing server2 sources...')
  import_server2()

  logging.info('Loading cache from "%s"' % load_file)
  cache = load_cache(load_file)
  logging.info('Saving cache to "%s"' % save_file)
  save_cache(save_file, cache)

  logging.info('done')

if __name__ == '__main__':
  _Main(sys.argv[1:])