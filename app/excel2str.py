#!/usr/bin/env python
# -*- coding:utf-8 -*-

from xml.dom import minidom
from xlrd import open_workbook
import codecs
import os


#######################################################
def mkdir(path):
    path = path.strip()
    path = path.rstrip("\\")

    isExists = os.path.exists(path)

    if not isExists:
        os.makedirs(path)
        print(path + ' created successfully')
        return True
    else:
        print(path + ' dir existed')
        return False


def run():
    workbook = open_workbook('Language.xls')
    sheet = workbook.sheet_by_index(0)

    for col_index in range(sheet.ncols):
        if col_index > 0:
            xml_doc = minidom.Document()
            en_resources = xml_doc.createElement('resources')
            language = sheet.cell(0, col_index).value
            for row_index in range(sheet.nrows):
                if row_index != 0:
                    key = sheet.cell(row_index, 0).value
                    result_content = sheet.cell(row_index, col_index).value
                    if (key != '' and result_content != ''):
                        print("key = %s, content = %s" % (key, result_content))
                        text_element = xml_doc.createElement('string')
                        text_element.setAttribute('name', key)
                        text_element.appendChild(xml_doc.createTextNode(str(result_content)))
                        en_resources.appendChild(text_element)
            xml_doc.appendChild(en_resources)
            mkdir("src/main/res/" + language)
            file = codecs.open('src/main/res/' + language + '/strings.xml', 'w', encoding='utf-8')
            file.write(xml_doc.toprettyxml(indent='    '))
            file.close()


#######################################################

if __name__ == '__main__':
    run()
